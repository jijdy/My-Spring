package springframework.beans.factory.config;

import springframework.beans.PropertyValues;

/*
是一个bean对象的包装类，用于存储bean对象并进行一定的相关操作,
加入字段属性，用于保存bean对象中的字段信息
 */
public class BeanDefinition {

    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;
    private boolean singleton = true;
    private boolean prototype = false;

    private String scope = SCOPE_SINGLETON;

    private final Class<?> beanClass;
    private final PropertyValues fieldValue;

    //用于存储该bean的初始化方法信息和销毁方法信息
    private String initMethodName;
    private String destroyMethodName;


    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        this.fieldValue = new PropertyValues();
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues fieldValue) {
        this.beanClass = beanClass;
        this.fieldValue = fieldValue != null ? fieldValue : new PropertyValues();
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public PropertyValues getFieldValue() {
        return fieldValue;
    }

    //设置作用域，并标记作用域
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public String getScope() {
        return scope;
    }
}
