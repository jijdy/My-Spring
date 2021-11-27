package springframework.beans.factory;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {


    /**
     * @author 蒋遥/jijdy
     * @createTime 2021/10/26 10:55
     * @description 按照类型返回bean对象的集合数组，类似@Autowired
     * 还可通过名称返回等，如@Resource就是默认按照name进行注入判断
     * @return
     */
    <T> Map<String, T> getBeansOfType(Class<T> type);

    //返回bean定义注册表中所有bean的名称
    String[] getBeanDefinitionNames();
}