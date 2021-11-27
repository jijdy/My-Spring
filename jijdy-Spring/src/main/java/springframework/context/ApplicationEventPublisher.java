package springframework.context;

//事件的发布接口，所有的事件都需要从这里发布出去
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);
}
