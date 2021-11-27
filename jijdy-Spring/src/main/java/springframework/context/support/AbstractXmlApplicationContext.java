package springframework.context.support;

import springframework.beans.factory.support.DefaultListableBeanFactory;
import springframework.beans.factory.xml.XMLBeanDefinitionReader;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/28 10:28
 * @Description 用于实现loadBeanDefinition方法，将配置资源中的bean属性加载到bean定义集合中
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    @Override
    protected void loadBeanDefinition(DefaultListableBeanFactory beanFactory) {
        XMLBeanDefinitionReader beanDefinitionReader = new XMLBeanDefinitionReader(beanFactory,this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    //极致的解耦，就是四五个抽象类实现一个功能链路，
    //这大概是大框架的通病吧，需要后期进行扩展的太多了，每一个可能会拓展的点都使用
    //了一个单独的类来进行实现之后就可以很轻松的个添加新功能到框架上
    //缺点大概就是有点费头发吧、。。。
    protected abstract String[] getConfigLocations();
}
