package springframework.beans.factory.support;

import springframework.core.io.DefaultResourceLoader;
import springframework.core.io.Resource;
import springframework.core.io.ResourceLoader;
/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/25 18:22
 * @Description 使用抽象类来实现接口中的必备方法，以免污染实现类，
 * 在实现类中就不需要关心其调用的方法的具体写法减少类方法间的耦合
 * 提供了bean定义注册对象和资源加载器对象，便于直接将bean注入到容器中
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private final BeanDefinitionRegistry registry;

    private final ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
