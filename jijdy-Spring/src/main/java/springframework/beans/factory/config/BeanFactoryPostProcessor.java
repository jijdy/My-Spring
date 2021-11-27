package springframework.beans.factory.config;

import cn.hutool.core.bean.BeanException;
import springframework.beans.factory.ConfigurableListBeanFactory;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/26 14:47
 * @Description  接口满足所有在Bean对象被定义到BeanDefinition之后，实例到bean容器之前
 * 提供对bean对象及其中属性property的修改方法(即BeanDefinition)
 */
public interface BeanFactoryPostProcessor {

    /**
     * @description 在所有beanDefinition载入完成后，实例化bean对象之前，提供更改bean定义的机制
     * @author 蒋遥/jijdy
     * @param: beanFactory
     * @createTime 2021/10/28 11:21
     */
    void postProcessBeanFactory(ConfigurableListBeanFactory beanFactory) throws BeanException;

}
