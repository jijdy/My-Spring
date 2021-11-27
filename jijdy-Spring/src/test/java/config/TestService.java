package config;

import springframework.beans.factory.DisposableBean;
import springframework.beans.factory.InitializingBean;

public class TestService implements DisposableBean, InitializingBean {
    public String string;

    private String name;

    private TestDao testDao;

    public void queryUserInfo() {
        System.out.println("string="+string+"/ name="+name+"/ testDao="+testDao);
    }

    @Override
    public String toString() {
        return "TestService{" +
                "string='" + string + '\'' +
                ", name='" + name + '\'' +
                ", testDao=" + testDao +
                '}';
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("这里是继承了DisposableBean接口的bean对象实现的destroy方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("这里是继承了InitializingBean接口的bean对象实现的afterPropertiesSet方法");
    }
}
