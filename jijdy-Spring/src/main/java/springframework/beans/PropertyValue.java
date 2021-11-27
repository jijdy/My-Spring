package springframework.beans;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/24 15:29
 * @Description 用于存储字段属性信息
 */
public class PropertyValue {
    private final String propertyName;

    private final Object propertyValue;

    public PropertyValue(String propertyName, Object propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }
}
