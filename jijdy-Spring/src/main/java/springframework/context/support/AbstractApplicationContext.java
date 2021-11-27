package springframework.context.support;

import cn.hutool.core.bean.BeanException;
import springframework.beans.BeansException;
import springframework.beans.factory.ConfigurableListBeanFactory;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanFactoryPostProcessor;
import springframework.beans.factory.config.BeanPostProcessor;
import springframework.context.ApplicationEvent;
import springframework.context.ApplicationListener;
import springframework.context.ConfigurableApplicationContext;
import springframework.context.event.ApplicationEventMulticaster;
import springframework.context.event.ContextRefreshEvent;
import springframework.context.event.SimpleApplicationEventMulticaster;
import springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/** 
 * @author 蒋遥/jijdy
 * @createTime 2021/10/26 15:11 
 * @Description
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;


    /**
     * @description 创建一个新的beanFactory，并通过资源文件中的更改，进行一次新的bean加载操作
     * 会检查执行器，执行器中的修改也会执行，并通过此修改bean对象的相关属性
     * @author 蒋遥/jijdy
     * @createTime 2021/10/28 11:27
     */
    @Override
    public void refresh() throws BeanException {

        //1.重新创建BeanFactory，并加载BeanDefinition(从配置中loadBeanDefinition)
        refreshBeanFactory();

        //2.获取新加载的BeanFactory
        ConfigurableListBeanFactory beanFactory = getBeanFactory();

        //3.将应用上下文包装到一个postProcessor中，并在之后在createBean的初始化方法中
        //若有bean实现了感知接口，则通过BeanPostProcessor接口调用拿到上下文的链接
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //4.在Bean对象实例化之前，执行BeanFactoryPostProcess，将得到的前置后置执行器加载到工厂中
        //即在上下文中加载执行器，此阶段可以对 bean定义 进行更改操作
        invokeBeanFactoryPostProcessors(beanFactory);

        //5.BeanPostProcessor的注册操作，将其写入到存储执行器的存储位置中，需要提前在Bean对象实例化前进行
        registerBeanPostProcessors(beanFactory);

        //6.初始化事件发布者
        initApplicationEventMulticaster();

        //7. 注册事件监听器
        registerListeners();

        //8.提前实例化单例Bean对象，并将其注入到bean容器中
        beanFactory.preInstantiateSingletons();

        //9.发布容器事件刷新事件
        finishRefresh();
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    //完成刷新步骤，执行发布事件的方法调用multicastEvent(event)广播事件的方法，并调用监听器中的执行事件
    private void finishRefresh() {
        publishEvent(new ContextRefreshEvent(this));
    }

    /**
     * @description 从bean定义中拿到所有实现了监听器的实现类，并将其添加到初始化中创建的caster广播器中
     * @author 蒋遥/jijdy
     * @createTime 2021/11/3 10:28
     */
    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener<?> listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    //从上下文中获取beanFactory，并创建监听器对象注入到单例容器中
    private void initApplicationEventMulticaster() {
        ConfigurableListBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        //将简易的监听执行器对象注入到单例容器中
        beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME,applicationEventMulticaster);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        getBeanFactory().destroySingleton();
    }

    protected abstract void refreshBeanFactory() throws BeanException;

    protected abstract ConfigurableListBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);

        for (BeanFactoryPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);

        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    //将BeanFactory中的核心方法跳过该接口间接封装并调用，能够在上下文中完成bean的操作
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return getBeanFactory().getBean(beanName,args);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> tClass) throws BeanException {
        return getBeanFactory().getBean(beanName,tClass);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }
}
