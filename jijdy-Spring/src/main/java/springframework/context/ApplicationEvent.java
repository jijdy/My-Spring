package springframework.context;

import java.util.EventObject;

//用于抽象的定义事件对象，后续的事件都需要继承这个抽象类
public abstract class ApplicationEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
