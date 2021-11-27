package springframework.beans.factory.support;

import springframework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/24 9:16
 * @Description 使用Cglib框架来实现获取多参构造其和返回对应参数的bean对象,
 * 若无构造器，则返回无参对象，否则根据构造器参数类型和传入参数进行返回
 */
public class CglibInstantiationStrategy implements InstantiationStrategy{


    @Override
    @SuppressWarnings("warning")
    public Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor<?> constructor, Object... args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        //？疑问处
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });

        if (constructor == null) {
            return enhancer.create();
        }
        return enhancer.create(constructor.getParameterTypes(), args);
    }
}
