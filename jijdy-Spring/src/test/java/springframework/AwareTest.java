package springframework;

import config.AwareService;
import config.TestService;
import org.junit.Test;
import springframework.context.support.ClassPathXMLApplicationContext;

public class AwareTest {

    /**
     * @author 蒋遥/jijdy
     * @createTime 2021/10/30 22:05
     * @Description 通过xml上下文来加载资源配置，通过bean拿到bean对象，已经定义好的接口方法
     * 直接拿到需要感知的到对象数据
     */
    @Test
    public void test() {
        ClassPathXMLApplicationContext applicationContext = new ClassPathXMLApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        AwareService testService = applicationContext.getBean("awareService", AwareService.class);

        System.out.println(testService.toString());

    }
}
