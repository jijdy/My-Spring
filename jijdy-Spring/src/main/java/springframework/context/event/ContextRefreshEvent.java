package springframework.context.event;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/11/3 9:04
 * @Description spring中自定义的事件监听器，用于监听应用上下文中的刷新操作(refresh方法)
 */
public class ContextRefreshEvent extends ApplicationContextEvent{

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextRefreshEvent(Object source) {
        super(source);
    }
}
