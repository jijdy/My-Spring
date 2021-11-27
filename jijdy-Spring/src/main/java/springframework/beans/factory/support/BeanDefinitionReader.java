package springframework.beans.factory.support;

import springframework.core.io.Resource;
import springframework.core.io.ResourceLoader;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/25 18:20
 * @Description bean定义集合读取接口，用于定义读取bean定义的方法和规范
 */
public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource);

    void loadBeanDefinitions(Resource... resources);

    void loadBeanDefinitions(String location);

    void loadBeanDefinitions(String... locations);

}
