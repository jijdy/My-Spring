package springframework.beans.factory.support;

import springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/24 8:57
 * @Description 为解决多参数bean的创建而设立的实例化接口
 */
public interface InstantiationStrategy {

    Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor<?> constructor, Object... args);
}
