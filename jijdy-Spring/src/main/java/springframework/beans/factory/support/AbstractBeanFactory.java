package springframework.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import springframework.beans.BeansException;
import springframework.beans.factory.BeanFactory;
import springframework.beans.factory.FactoryBean;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanPostProcessor;
import springframework.beans.factory.config.ConfigurableBeanFactory;
import springframework.util.ClassLoaderUtil;

import java.util.ArrayList;
import java.util.List;

/** 
 * @author 蒋遥/jijdy
 * @createTime 2021/10/23 15:35 
 * @Description 将单例bean功能和getBean功能结合并实现，并提供抽象方法
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /* 用于beanPostProcessor的管理容器，在bean注册前载入并进行前置处理 */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final ClassLoader classLoader = ClassLoaderUtil.getDefaultClassLoader();

    public ClassLoader getBeanClassLoader() {
        return classLoader;
    }

    /**
     * @description 对拿到的beanPostProcessor进行注册操作，
     * @author 蒋遥/jijdy
     * @param: beanpostProcessor
     * @createTime 2021/10/28 9:15
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanpostProcessor) {
        beanPostProcessors.remove(beanpostProcessor);
        beanPostProcessors.add(beanpostProcessor);
    }

    /**
     * @author 蒋遥/jijdy
     * @createTime 2021/10/23 15:44
     * @Description 得到单例bean对象，若 容器 中无bean对象则从 bean定义集
     * 中拿到 beanDefinition 并重新创建bean对象注入bean容器中
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName);
    }


    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName,args);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> tClass) throws BeanException {
        return doGetBean(beanName,tClass);
    }

    /**
     * @author 蒋遥/jijdy
     * @param: beanName
     * @param: objects
     * @createTime 2021/10/26 9:51
     * @return: T
     * @description 泛用方法，能够通过泛型或非泛型来返回得到bean对象，
     * 同时也是模板模式中的模板方法，规定了从bean容器中拿到bean对象时的一系列流程
     * 若已经注册到bean容器中则直接放回，否则需要再拿到beanDefinition对象并创建bean注入到容器中
     */
    protected <T> T doGetBean(String beanName, Object... objects) throws BeanException{
        Object object = getSingleton(beanName);
        if (object != null) {
            return (T) getObjectForBeanInstance(object, beanName);
        }
        //若单例中无该对象，则从定义中拿出并执行一系列创建流程
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName,beanDefinition,objects);
        return (T) getObjectForBeanInstance(bean, beanName);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException;

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }
}
