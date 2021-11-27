package springframework.beans.factory.config;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/24 15:26
 * @Description 存储一个beanName信息，是一个对已有bean对象的引用，
 * 即将一个bean对象作为另一个bean对象的字段属性，
 */
public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
