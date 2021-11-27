package springframework.context.event;

import springframework.context.ApplicationContext;
import springframework.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    //获取引发事件的ApplicationContext
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
