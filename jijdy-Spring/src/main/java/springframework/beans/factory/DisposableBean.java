package springframework.beans.factory;

public interface DisposableBean {

    /**
     * @description 执行bean的销毁程序，将需要销毁的bean进行回收
     * @author 蒋遥/jijdy
     * @createTime 2021/10/29 15:04
     */
    void destroy() throws Exception;
}
