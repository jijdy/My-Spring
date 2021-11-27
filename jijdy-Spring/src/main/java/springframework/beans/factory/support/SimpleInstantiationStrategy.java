package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/24 9:01
 * @Description 使用jdk自带的api来实现多构造器bean对象的实例化操作
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy{

    @Override
    public Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor<?> constructor, Object... args) {
        Class<?> clazz = beanDefinition.getBeanClass();
        try {
            if (constructor == null) {
                return clazz.getDeclaredConstructor().newInstance();
            } else {
                return clazz.getDeclaredConstructor(constructor.getParameterTypes()).newInstance(args);
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new BeansException("Bean "+ beanName +" Instance failed!");
        }

    }
}
