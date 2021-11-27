package springframework;

import event.CustomEvent;
import org.junit.Test;
import springframework.context.support.ClassPathXMLApplicationContext;

public class EventTest {

    @Test
    public void eventTest() {
        ClassPathXMLApplicationContext applicationContext = new ClassPathXMLApplicationContext("classpath:spring2.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 445466656898L, "成功了！"));

        applicationContext.registerShutdownHook();
    }
}
