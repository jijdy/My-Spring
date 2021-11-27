package config;

import cn.hutool.core.bean.BeanException;
import springframework.beans.factory.BeanClassLoaderAware;
import springframework.beans.factory.BeanFactory;
import springframework.beans.factory.BeanFactoryAware;
import springframework.beans.factory.BeanNameAware;
import springframework.context.ApplicationContext;
import springframework.context.ApplicationContextAware;

public class AwareService implements BeanFactoryAware, BeanClassLoaderAware, BeanNameAware, ApplicationContextAware {

    public String string = "======awareService 中的字符串~=====";

    public ClassLoader classLoader;
    public BeanFactory beanFactory;
    public String beanName;
    public ApplicationContext applicationContext;


    @Override
    public void setBeanClassLoader(ClassLoader classLoader) throws BeanException {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String beanName) throws BeanException {
        this.beanName = beanName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
        this.applicationContext = applicationContext;
    }

    @Override
    public String toString() {
        return "AwareService{" +
                "string='" + string + '\'' +
                ", classLoader=" + classLoader +
                ", beanFactory=" + beanFactory +
                ", beanName='" + beanName + '\'' +
                ", applicationContext=" + applicationContext +
                '}';
    }
}
