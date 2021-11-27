package event;


import springframework.context.ApplicationListener;
import springframework.context.event.ContextRefreshEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }

}
