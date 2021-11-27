package springframework;

import org.junit.Test;
import springframework.context.ConfigurableApplicationContext;
import springframework.context.support.ClassPathXMLApplicationContext;

public class InitAndDestroyTest {

    @Test
    public void test() {
        ConfigurableApplicationContext applicationContext = new ClassPathXMLApplicationContext("classpath:spring.xml");
//        applicationContext.refresh();
//        applicationContext.registerShutdownHook();
//        applicationContext.close();
    }
}
