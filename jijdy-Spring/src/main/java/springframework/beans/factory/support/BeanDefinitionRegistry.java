package springframework.beans.factory.support;

import springframework.beans.factory.config.BeanDefinition;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/23 16:40
 * @Description 定义将beanDefinition对象注册到bean容器中的接口方法
 */
public interface BeanDefinitionRegistry {

    //将bean定义注册到bena定义集合中
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    //根据beanName从bean定义集合中拿到bean定义对象
    BeanDefinition getBeanDefinition(String beanName);

    //判断当前bena定义集合中是否含有该benaName
    boolean containsBeanDefinition(String beanName);

    //从bean定义集合中拿到所有beanName的字符串数组
    String[] getBeanDefinition();
}
