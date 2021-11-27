package springframework.context;

import springframework.beans.factory.HierarchicalBeanFactory;
import springframework.beans.factory.ListableBeanFactory;
import springframework.core.io.ResourceLoader;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/26 15:07
 * @Description 其间接继承了BeanFactory，所以有其所有功能，但其也是一个核心接口
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
