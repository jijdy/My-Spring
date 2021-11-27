package springframework.context;

import cn.hutool.core.bean.BeanException;
import springframework.beans.factory.Aware;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/30 20:30
 * @Description 能够拿到该bean所属的ApplicationContext引用上下文对象
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeanException;
}
