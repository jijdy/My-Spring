package springframework.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import springframework.beans.BeansException;
import springframework.beans.PropertyValue;
import springframework.beans.PropertyValues;
import springframework.beans.factory.*;
import springframework.beans.factory.config.AutowireCapableBeanFactory;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanPostProcessor;
import springframework.beans.factory.config.BeanReference;
import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/23 15:48
 * @Description 功能分离，用于将bean对象注入到bean容器中
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibInstantiationStrategy();

    /**
     * @author 蒋遥/jijdy
     * @createTime 2021/10/23 16:02
     * @Description 使用newInstance()方法获得到的实例对象，只是该对象有默认无参构造函数的对象，该对象没有无参构造，则会报错。
     * //使用cglib框架构造的多参数构造器对象获取，对长度进行校检并拿取到相同长度的constructor构造器，
     * 有一定的错误率，未将类型进行一一对应处理
     * 作为模板方法中的一部分，不直接被外部方法调用，完成bean对象的获取和穿件并注入到 单例容器 中
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException {
        Object bean = null;
        try {
           //执行bean的class实例创建和bean的字段属性的填充

           bean = createBeanInstance(beanName, beanDefinition, args);
           applyPropertyValues(beanName, bean, beanDefinition);

           //执行bean的初始化方法，和beanPostProcessor的前置和后置执行器
           bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
           throw new BeansException("create bean '"+beanName+"' field"+e);
        }

        //将销毁方法放入到销毁注册容器中
        registerDisposableBeanIfNecessary(beanName,bean,beanDefinition);

        //添加到对应的单例bean容器中，为后续进行bean获取,若不是单例作用域，则不加入
//        System.out.println("是否是单例："+beanDefinition.isSingleton());
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName,bean);
        }
        return bean;
    }

    protected Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object... args) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] constructors = beanClass.getConstructors();
        Constructor<?> constructor = null;
        //according into length with constructor,but not strict
        for (Constructor<?> constructor1 : constructors) {
            if (args != null && constructor1.getParameterCount() == args.length) {
                constructor = constructor1;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanName,beanDefinition,constructor,args);
    }

    //instanceof 的对象为接口，bean对象继承接口再进行重写
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        //非Singleton单例类型的bean不执行销毁方法(只会被调用一次，会被直接垃圾回收
        if (!beanDefinition.isSingleton()) {
            return;
        }

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean,beanName,beanDefinition));
        }
    }

    /**
     * @author 蒋遥/jijdy
     * @createTime 2021/10/24 18:35
     * @Description 对存储在bean定义集合中的字段属性进行拆解，并拿出注入到需要存储到bean容器中的bean对象中
     * 循环将属性写入到bean对象中，借用hutool-all框架来辅助完成bean中的字段替换和注入
     * 需要注意注入bean对象的字段名称需要和该对象中的字段名称一致，不然会报错
     */
    public void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {

        try {
            PropertyValues fieldValue = beanDefinition.getFieldValue();

            for (PropertyValue value : fieldValue.getPropertyValue()) {
                String propertyName = value.getPropertyName();
                Object propertyValue = value.getPropertyValue();

                if (propertyValue instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) propertyValue;
                    propertyValue = getBean(beanReference.getBeanName());
                }
                //注意，这里将属性注入时需要保证name和bean中的字段名一致，不然会失败
                BeanUtil.setFieldValue(bean, propertyName, propertyValue);
            }
        } catch (Exception e) {
            throw new BeansException("Bean PropertyValue Autowire Field with:"+beanName);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        //bean的其他一些简单的相关配置信息，直接通过类型进行判断，并加载到相关bean，作为动态链接加载
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
        }

        //1. 执行BeanPostProcessor的before前置处理，
        //应用上下文的感知连接通过此进行操作
        Object wrappedBean = applyBeanPostProcessorBeforeInitialization(bean,beanName);

        //初始化方法
        try {
            invokeInitMethods(beanName,wrappedBean,beanDefinition);
        } catch (Exception e) {
            throw new BeansException("invoke init method of bean '"+beanName+"' field");
        }

        //2. 执行后置处理
        wrappedBean = applyBeanPostProcessorAfterInitialization(bean,beanName);

        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception{
        //判断这个bean是否有继承对外暴露的接口，并直接调用执行这个方法，
        //也可以看做是初始化方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        //判断init-method信息，通过反射从bean定义中拿到Method方法对象
        String initMethodName = beanDefinition.getInitMethodName();

        if (StrUtil.isNotEmpty(initMethodName)) {
            Method methods = beanDefinition.getBeanClass().getMethod(initMethodName);

            if (ObjectUtil.isNull(methods)) {
                throw new BeansException("invoke init method '"+initMethodName+"'on bean with '"+beanName+"' field!");
            }
            //执行初始化inti方法
            methods.invoke(bean);
        }
    }

    /**
     * @description 通过接口完成实现类的编程，通过用户预定义的实现类，来使其拥有对bean定义和
     * bean容器中的bean对象的修改能力，
     * 不能够有多与的执行器，若有执行器返回对象为null，则bean的执行器执行操作不能确定在什么地方就会结束
     * @author 蒋遥/jijdy
     * @param: existingBean
     * @param: beanName
     * @createTime 2021/10/28 12:49
     * @return: java.lang.Object
     */
    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for (BeanPostProcessor postProcessor : getBeanPostProcessors()) {
            Object current = postProcessor.postProcessBeforeInitialization(beanName,result);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    /**
     * @description 后置操作，在bean被初始化之后执行的操作
     * @author 蒋遥/jijdy
     * @param: existingBean
     * @param: beanName
     * @createTime 2021/10/28 12:52
     * @return: java.lang.Object
     */
    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for (BeanPostProcessor postProcessor : getBeanPostProcessors()) {
            Object current = postProcessor.postProcessAfterInitialization(beanName, result);
            if (null == current) return result;
            result = current;
        }
        return result;
    }
}
