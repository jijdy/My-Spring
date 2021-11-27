package springframework;

import org.junit.Test;

public class AnotherTest {
    @Test
    public void classTest() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("config.TestService");
    }
}
