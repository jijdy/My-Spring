package springframework.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import springframework.beans.BeansException;
import springframework.beans.factory.ConfigurableListBeanFactory;
import springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/** 
 * @author 蒋遥/jijdy
 * @createTime 2021/10/23 17:00 
 * @Description 核心类，用于存储beanDefinition定义的容器，得到bean定义和注册bean定义对象
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListBeanFactory {
    
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (null == beanDefinition) {
            throw new BeansException("get BeanDefinition named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    /**
     * @description 调用bean定义集中的所有bean的key，并使用表达式进行bean容器的装配
     * @author 蒋遥/jijdy
     * @createTime 2021/10/28 8:58
     */
    @Override
    public void preInstantiateSingletons() throws BeanException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public String[] getBeanDefinition() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * @description 根据类型来返回bean中所有与该类型的bean对象，使用匿名写法
     * @author 蒋遥/jijdy
     * @param: type
     * @createTime 2021/10/28 9:06
     * @return: T[]
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    /**
     * @description 拿到所有在bean定义中的beanName
     * @author 蒋遥/jijdy
     * @createTime 2021/10/28 9:01
     * @return: java.lang.String[]
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
}
