package springframework.beans.factory.support;


import springframework.beans.BeansException;
import springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    //用于缓存单例类型的对象，避免重复创建
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String BeanNaem) {
        Object object = this.factoryBeanObjectCache.get(BeanNaem);
        return (object != NULL_OBJECT ? object : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        if (factoryBean.isSingleton()) {
            Object o = this.factoryBeanObjectCache.get(beanName);
            if (o == null) {
                o = doGetObjectFromFactoryBean(factoryBean,beanName);
                this.factoryBeanObjectCache.put(beanName, (o != null ? o : NULL_OBJECT));
            }
            return o != NULL_OBJECT ? o : null;
        } else {
            //若不是单例，则直接返回，不需要加载到缓存位置中
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }
    }

    //通过FactoryBean接口直接调用获取bean对象,具体操作有实现类关注
    protected Object doGetObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("factoryBean throw exception on object '"+beanName+"' creation", e);
        }
    }
}
