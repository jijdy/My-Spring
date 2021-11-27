package springframework.context;

import cn.hutool.core.bean.BeanException;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/26 15:09
 * @Description 承接了应用上下文，主要定义刷新容器的操作，将更改的bean重新加载
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * @description 刷新bean容器，
     * @author 蒋遥/jijdy
     * @createTime 2021/10/26 15:09
     */
    void refresh() throws BeanException;

    /**
     * @Description 注册虚拟机钩子，虚拟机钩子能够添加一个新线程
     * 该线程会在守护线程结束运行前，即虚拟机执行关闭操作前调用这个新线程
     * @author 蒋遥/jijdy
     * @createTime 2021/10/29 16:49
     */
    void registerShutdownHook();

    /**
     * @description 手动执行关闭操作
     * @author 蒋遥/jijdy
     * @createTime 2021/10/29 16:49
     */
    void close();
}
