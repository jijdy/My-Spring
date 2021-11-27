package springframework.context.support;

import cn.hutool.core.bean.BeanException;
import springframework.beans.factory.ConfigurableListBeanFactory;
import springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/28 10:03
 * @Description 用于扩展应用上下文，用于创建一个新的DefaultListableBeanFactory对象并获取
 * 并将其设定与加载资源配置文件进行定义，作为父抽象类定义方法
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory beanFactory;

    /**
     * @description 返回一个DefaultListableBeanFactory并完成该工厂的配置加载操作(子类实现)
     * @author 蒋遥/jijdy
     * @createTime 2021/10/28 10:17
     */
    @Override
    protected void refreshBeanFactory() throws BeanException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinition(beanFactory);
        this.beanFactory = beanFactory;
    }

    protected abstract void loadBeanDefinition(DefaultListableBeanFactory beanFactory);

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    protected ConfigurableListBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
