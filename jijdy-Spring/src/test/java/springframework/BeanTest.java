package springframework;

import springframework.beans.PropertyValue;
import springframework.beans.PropertyValues;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanReference;
import springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.hutool.core.bean.BeanUtil;
import config.TestService;
import org.junit.Test;
import springframework.beans.factory.xml.XMLBeanDefinitionReader;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class BeanTest {


    /**
     * @author 蒋遥/jijdy
     * @createTime 2021/10/23 18:38
     * @Description 对bean容器的注册，拿取与单例bean的实现进行测试，
     * 将所有bean交给一个bean容器进行管理，单例完成，但多次调用得到的bean没有进行隔离
     */
    @Test
    public void beanTest() {
        BeanDefinition beanDefinition1 = new BeanDefinition(TestService.class);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.registerBeanDefinition("BeanTest",beanDefinition1);

        TestService beanTest = (TestService) beanFactory.getBean("BeanTest");
        System.out.println(beanTest.string);//null
        beanTest.string = "test";
        TestService beanTest2 = (TestService) beanFactory.getBean("BeanTest");
        System.out.println(beanTest2.string);//test
    }

    @Test
    public void beanTest2() {
//        TestService bean = new TestService();
        BeanDefinition beanDefinition = new BeanDefinition(TestService.class);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //将beanDefinition定义到bean定义集合中，便于注入时作为bean定义类进行类关系传递
        beanFactory.registerBeanDefinition("TestService",beanDefinition);
        //首次bean只定义，还未注入到beanFactory的容器中，会从bean定义中拿到对应的bean类信息
        //并传递到bean容器中，进行注入操作，注入成功就将其从bean容器中拿出
        TestService testService = (TestService) beanFactory.getBean("TestService","test");
        testService.queryUserInfo(); //test，参数传递成功

    }

    @Test
    public void parameterTest() {
        Constructor<?> constructor = TestService.class.getConstructors()[1];
        //java.lang.String,参数类型为该传入参数的数据类型或对应类
        System.out.println(Arrays.toString(constructor.getParameterTypes()));
    }

    @Test
    public void constructorTest() throws NoSuchMethodException {
        BeanDefinition beanDefinition = new BeanDefinition(TestService.class);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("test",beanDefinition);

        TestService service2 = (TestService) beanFactory.getBean("test");
        TestService service = (TestService) beanFactory.getBean("test", "kkkkllll");
//        System.out.println(Arrays.toString(service.getClass().getConstructor().getParameterTypes()));
//        System.out.println(Arrays.toString(service2.getClass().getConstructor().getParameterTypes()));
        service.queryUserInfo();
        service2.queryUserInfo();
    }

    //测试hutool-all框架中的BeanUtil类中的属性注入功能
    @Test
    public void beanUtilTest() {
        TestService testService = new TestService();
        System.out.println(testService);
        String s = "lllll";
        //将属性注入到对象中，根据字段名称或字段下标，
        BeanUtil.setFieldValue(testService,"anInt",s);
        System.out.println(testService.toString());
    }

    @Test
    public void propertyTest() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition1 = new BeanDefinition(TestService.class);
        beanFactory.registerBeanDefinition("TestService",beanDefinition1);

        PropertyValues values = new PropertyValues();
        values.addPropertyValue(new PropertyValue("anInt","写入的属性"));

        values.addPropertyValue(new PropertyValue("TestService",new BeanReference("TestService")));
        BeanDefinition beanDefinition = new BeanDefinition(TestService.class,values);
        beanFactory.registerBeanDefinition("testService",beanDefinition);

        //这里低二次循环到去TestService成功，
        System.out.println(beanFactory.getBean("testService")); //属性导入成功
        System.out.println(beanFactory.getBean("testService","test3")); //传入参数未起作用
    }

    //测试xml的bean注入
    @Test
    public void xmlTest() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XMLBeanDefinitionReader beanDefinitionReader = new XMLBeanDefinitionReader(beanFactory);

        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        //注入成功，也能够将bean定义集合中的对象作为引用进行传递使用
        System.out.println(beanFactory.getBean("userService").toString());
        System.out.println(beanFactory.getBean("userDao"));
        //泛型方法的测试，能够使用
        TestService userService = beanFactory.getBean("userService", TestService.class);
        System.out.println(userService+"dsaf");
    }
}
