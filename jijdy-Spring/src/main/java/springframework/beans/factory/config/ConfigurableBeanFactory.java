package springframework.beans.factory.config;

import springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/26 11:16
 * @Description 可以获取BeanPostProcessor,BeanClassLoader等对象的配置化接口
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory,SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanpostProcessor);
}