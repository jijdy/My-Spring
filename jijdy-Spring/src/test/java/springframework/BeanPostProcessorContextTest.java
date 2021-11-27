package springframework;

import config.TestService;
import org.junit.Test;
import service.beanpostprocessor.MyBeanFactoryPostProcessor;
import service.beanpostprocessor.MyBeanPostProcessor;
import springframework.beans.factory.ConfigurableListBeanFactory;
import springframework.beans.factory.config.ConfigurableBeanFactory;
import springframework.beans.factory.support.DefaultListableBeanFactory;
import springframework.beans.factory.xml.XMLBeanDefinitionReader;
import springframework.context.ConfigurableApplicationContext;
import springframework.context.support.ClassPathXMLApplicationContext;

public class BeanPostProcessorContextTest {

    @Test
    public void beanFactoryPostProcessorTest() {
        //1.初始化beanFactory对象
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //2.得到xml的配置读取对象
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        //3.在beanDefinition定义之后，bean对象实例化之前，进行beanFactoryPostProcessor对象操作
        MyBeanFactoryPostProcessor myBeanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        myBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        //3.5 的bean被实例化之后，但在被注入到容器之前进行操作，
        MyBeanPostProcessor myBeanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(myBeanPostProcessor);

        //4.测试操作是否成功
        TestService userService = beanFactory.getBean("userService", TestService.class);;
        System.out.println(userService.toString());

        //测试成功：上下文的调用能够成功的运行，能够更改BeanDefinition中的定义，也可以在
        //其被注入到容器之前，但实例化之后，修改bean中的数值
        //最后，这些修改都是基于BeanName来进行的，

        ConfigurableApplicationContext context = new ClassPathXMLApplicationContext("classpath:spring.xml");
        //通过刷新操作，可以直接通过应用上下文来对BeanFactoryPostProcessor对象进行自动检测，并完成在bean实例化之前的bean属性更改
        //但是需要将改执行器载入到beanDefinition定义中，即将改对象也定义为bean对象，交给bean容器管理，
        //这类bean对象在SpringBoot中一般使用@Component或@Configuration等注解表示，即交给了bean管理，也直接完成了该对象需要完成的任务
        context.refresh();
        System.out.println(context.getBean("userService"));
    }
}
