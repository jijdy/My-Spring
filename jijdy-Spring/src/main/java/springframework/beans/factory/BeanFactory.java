package springframework.beans.factory;

import cn.hutool.core.bean.BeanException;
import springframework.beans.BeansException;
import springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

//主接口，提供了获取bean实例的功能
public interface BeanFactory {

    Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    Object getBean(String beanName) throws BeansException;

    Object getBean(String beanName, Object... args) throws BeansException;

    <T> T getBean(String beanName, Class<T> tClass) throws BeanException;

    //添加之后可直接通过该接口调用完成BeanDefinition对象的定义操作
    void registerBeanDefinition(String name, BeanDefinition beanDefinition);


}
