package springframework.beans.factory;


/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/29 15:02
 * @Description 初始化bean的标签定义方法，在bean对象的属性写入之后执行
 */
public interface InitializingBean {

    /**
     * @description BeanDefinition在属性填充之后进行调用的方法，对其初始化
     * 的定义进行判断和设置
     * @author 蒋遥/jijdy
     * @createTime 2021/10/29 15:01
     */
    void afterPropertiesSet() throws Exception;

}
