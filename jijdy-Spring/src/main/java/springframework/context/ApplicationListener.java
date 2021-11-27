package springframework.context;

import java.util.EventListener;

public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    //处理事件的方法
    void onApplicationEvent(E event);
}
