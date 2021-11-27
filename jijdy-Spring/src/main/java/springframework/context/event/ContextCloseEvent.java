package springframework.context.event;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/11/3 9:05
 * @Description spring中自定义的事件监听器，用于监听应用上下文中的关闭操作(close方法)
 */
public class ContextCloseEvent extends ApplicationContextEvent{

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextCloseEvent(Object source) {
        super(source);
    }
}
