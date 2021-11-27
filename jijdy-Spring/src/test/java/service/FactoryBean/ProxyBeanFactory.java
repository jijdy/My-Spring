package service.FactoryBean;

import springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyBeanFactory implements FactoryBean<IUserDao> {

    //添加一个代理对象，并进行代理处理操作,在进行原型调用时，就会进行代理操作
    @Override
    public IUserDao getObject() throws Exception {

        //调用的是InvocationHandler的invoke方法，此方法为重写，并返回一个String对象
        InvocationHandler handler = (proxy,method,args) -> {

            if (method.getName().equals("toString")) return this.toString();

            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("1001","test1");
            hashMap.put("002","test2");
            hashMap.put("003","test3");

            return "被代理执行的方法" + method.getName() + ":" + hashMap.get(args[0].toString());
        };
        //这里相当于新建了一个IUserDao对象作为bean返回和使用，通过代理类来实现代理模式
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{IUserDao.class},handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
