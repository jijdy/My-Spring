package springframework.context.support;

import cn.hutool.core.bean.BeanException;
import springframework.beans.factory.config.BeanPostProcessor;
import springframework.context.ApplicationContext;
import springframework.context.ApplicationContextAware;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/30 21:15
 * @Description 本质是一个包装类，用于包装在refresh方法中进行调用，
 * 并在bean的前置化操作中拿到这个applicationContext对象进行存储
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    /**
     * @description 在进行bean实例化之后，调用beanPostProcessor的前置化操作时
     * 将于该bean对应的ApplicationContext对象存储到bean中，进行感知操作(创建动态链接)
     * @author 蒋遥/jijdy
     * @param: beanName
     * @param: bean
     * @createTime 2021/10/30 20:59
     * @return: java.lang.Object
     */
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) throws BeanException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) throws BeanException {
        return bean;
    }
}
