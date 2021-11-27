package springframework.beans.factory;

import cn.hutool.core.bean.BeanException;
import springframework.beans.factory.config.AutowireCapableBeanFactory;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanPostProcessor;
import springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/26 11:23
 * @Description 提供分析和修改Bean及预先实例化的操作，的接口
 */
public interface ConfigurableListBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {

    BeanDefinition getBeanDefinition(String name);

    void preInstantiateSingletons() throws BeanException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
