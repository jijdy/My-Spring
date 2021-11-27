package springframework;

import org.junit.Test;
import service.FactoryBean.ProxyBeanFactory;
import service.FactoryBean.UserService;
import springframework.context.support.ClassPathXMLApplicationContext;

public class FactoryBeanTest {
    @Test
    public void test() {
        ClassPathXMLApplicationContext applicationContext = new ClassPathXMLApplicationContext("classpath:spring.xml");

        UserService userService1 = applicationContext.getBean("userService1", UserService.class);
        UserService userService2 = applicationContext.getBean("userService1", UserService.class);

        //scope="singleton"时，得到的是同一个对象；
        //scope=“prototype”时，得到的是不同的原型对象，
        //并且不会将bean对象从定义集合中实例化到容器中，每次调用时都会重新调用
        System.out.println(userService1);
        System.out.println(userService2);
    }

    @Test
    public void proxyTest() {
        ClassPathXMLApplicationContext applicationContext = new ClassPathXMLApplicationContext("classpath:spring.xml");
        UserService proxyBean = applicationContext.getBean("userService1", UserService.class);

        System.out.println(proxyBean.printInfo());
    }
}
