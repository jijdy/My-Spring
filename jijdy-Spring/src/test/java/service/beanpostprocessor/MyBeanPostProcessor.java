package service.beanpostprocessor;

import cn.hutool.core.bean.BeanException;
import config.TestService;
import springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) throws BeanException {
        if ("userService".equals(beanName)) {
            TestService testService = (TestService) bean;
            testService.string = new String(new StringBuilder(testService.string).append("  /t在beanDefinition被定义之后添加的数据"));
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) throws BeanException {
        return bean;
    }
}
