package springframework.beans.factory.config;

/*
单例bean的注册接口，用于定义单例化操作
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    void destroySingleton();

    void addSingleton(String BeanName, Object bean);
}
