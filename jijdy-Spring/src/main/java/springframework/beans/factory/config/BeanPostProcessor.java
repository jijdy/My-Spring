package springframework.beans.factory.config;

import cn.hutool.core.bean.BeanException;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/26 14:52
 * @Description 提供了在bean加载实例化之后对其进行修改的能力，使用前置和后置处理器完成
 */
public interface BeanPostProcessor {

    /**
     * @description 在bean对象初始化操作之前的前置处理器
     * @author 蒋遥/jijdy
     * @param: beanName
     * @param: bean
     * @createTime 2021/10/26 14:55
     * @return: java.lang.Object
     */
    Object postProcessBeforeInitialization(String beanName, Object bean) throws BeanException;

    /**
     * @description 在bean对象指向初始化方法之后执行的处理器
     * @author 蒋遥/jijdy
     * @param: beanName
     * @param: bean
     * @createTime 2021/10/26 15:02
     * @return: java.lang.Object
     */
    Object postProcessAfterInitialization(String beanName, Object bean) throws BeanException;
}
