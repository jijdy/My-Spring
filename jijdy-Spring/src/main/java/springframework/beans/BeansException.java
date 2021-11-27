package springframework.beans;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/23 15:51
 * @Description 定义bean异常，完全依靠可运行异常完成异常的抛出操作
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
