package springframework.beans.factory.support;

import springframework.beans.BeansException;
import springframework.beans.factory.DisposableBean;
import springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/23 15:27
 * @Description 实现了单例bean的单例化操作并进行存储。
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new HashMap<>();

    private Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    protected static final Object NULL_OBJECT = new Object();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * @author 蒋遥/jijdy
     * @createTime 2021/10/23 15:33
     * @Description 该方法供给其子类使用，用于向bean容器中添加bean对象
     */
    public void addSingleton(String beanName, Object singletonBean) {
        singletonObjects.put(beanName,singletonBean);
    }

    //将销毁方法的对象额外注册到该容器中
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeanMap.put(beanName,bean);
    }

    /**
     * @description 执行销毁容器中bean对象的销毁方法,倒叙删除
     * 通过在创建bean时导入到适配器中bean对象的引用，和销毁方法信息，调用destroy()方法执行销毁
     * @author 蒋遥/jijdy
     * @createTime 2021/10/29 16:43
     */
    @Override
    public void destroySingleton() {
        Set<String> strings = disposableBeanMap.keySet();
        Object[] objects = strings.toArray();
//        System.out.println("=="+ Arrays.toString(objects));
        for (Object object : objects) {
            DisposableBean bean = disposableBeanMap.remove(object);
            try {
                bean.destroy();
            } catch (Exception e) {
                throw new BeansException("destroy bean is field, throw a exception: "+e);
            }
        }
    }

}
