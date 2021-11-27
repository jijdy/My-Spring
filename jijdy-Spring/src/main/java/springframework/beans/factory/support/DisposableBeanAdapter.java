package springframework.beans.factory.support;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import springframework.beans.BeansException;
import springframework.beans.factory.DisposableBean;
import springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @description 销毁bean的一个适配器，不确定调用销毁的是类或者是接口，不需要用户关心调用的对象
 * 的类型，所以使用适配器进行一个统一的操作
 * @author 蒋遥/jijdy
 * @createTime 2021/10/29 15:54
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        //1. 实现disposableBean接口并直接调用方法
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        //2. 获取配置信息中的destroy方法信息并执行,避免重复定义和执行
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean)) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (ObjectUtil.isNull(destroyMethod)) {
                throw new BeansException("could not find destroy method name '"
                        +destroyMethod+"' on bean '"+beanName+"!");
            }
            destroyMethod.invoke(bean);
        }
    }
}
