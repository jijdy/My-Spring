package springframework.context.event;

import cn.hutool.core.bean.BeanException;
import springframework.beans.BeansException;
import springframework.beans.factory.BeanFactory;
import springframework.beans.factory.BeanFactoryAware;
import springframework.context.ApplicationEvent;
import springframework.context.ApplicationListener;
import springframework.util.ClassLoaderUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/11/3 9:59
 * @Description 提供了监听器的存储和获取操作，根据事件获取其对于的所有监听器，以便之后进行广播操作
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;


    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = beanFactory;
    }

    /**
     * @description 通过event对象来获取事件所对应的所有监听器
     * @author 蒋遥/jijdy
     * @param: event
     * @createTime 2021/11/3 9:35
     * @return: java.util.Collection<springframework.context.ApplicationListener<?>>
     */
    protected Collection<ApplicationListener<?>> getApplicationListeners(ApplicationEvent event) {
        List<ApplicationListener<?>> allListener = new LinkedList<>();

        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
            if (supportEvent(applicationListener, event)) allListener.add(applicationListener);
        }
        return allListener;
    }

    /**
     * @description 用于判断监听器是否和事件有继承关系，感兴趣
     * @author 蒋遥/jijdy
     * @param: listener
     * @param: event
     * @createTime 2021/11/3 9:38
     * @return: boolean
     */
    protected boolean supportEvent(ApplicationListener<?> listener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = listener.getClass();

        // 按照 CglibSubclassingInstantiationStrategy、SimpleInstantiationStrategy 不同的实例化类型，需要判断后获取目标 class
        //若为cglib代理则需要获取其父类对象，否则直接获取对象即可
        Class<?> targetClass = ClassLoaderUtil.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        Type genericInterface = targetClass.getGenericInterfaces()[0];

        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        // 判定此 eventClassName 对象所表示的类或接口与指定的 event.getClass() 参数所表示的类或接口是否相同，或是否是其超类或超接口。
        // isAssignableFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有的类的终极父类都是Object。如果A.isAssignableFrom(B)结果是true，证明B可以转换成为A,也就是A可以由B转换而来。
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
