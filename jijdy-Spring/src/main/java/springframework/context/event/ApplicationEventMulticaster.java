package springframework.context.event;

import springframework.context.ApplicationEvent;
import springframework.context.ApplicationListener;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/11/3 9:19
 * @Description 监听器的接口定义，添加和删除，以及具体监听事件
 */
public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
}
