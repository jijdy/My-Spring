package springframework.beans.factory;

import cn.hutool.core.bean.BeanException;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/30 20:28
 * @Description 通过该接口能够感知到该bean在bean容器中所对应的beanName
 */
public interface BeanNameAware extends Aware{

    void setBeanName(String beanName) throws BeanException;
}
