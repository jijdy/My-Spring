package springframework.beans.factory;

import cn.hutool.core.bean.BeanException;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/30 20:23
 * @Description 外部用户的通过继承该接口，能够感知到其所属的BeanFactory对象
 */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory) throws BeanException;
}
