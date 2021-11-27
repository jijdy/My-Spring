package springframework.context.event;

import springframework.beans.factory.BeanFactory;
import springframework.context.ApplicationEvent;
import springframework.context.ApplicationListener;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/11/3 10:13
 * @Description 抽象监听集合的简易实现类，通过传入的事件完成对各个监听器的调用事件处理
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
