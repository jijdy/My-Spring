package springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/24 15:32
 * @Description 用于存储字段属性集合，使用list进行存储，操作时直接进行add操作
 */
public class PropertyValues {
    private final List<PropertyValue> list = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        this.list.add(propertyValue);
    }

    public PropertyValue[] getPropertyValue() {
        return this.list.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue value : getPropertyValue()) {
            if(value.getPropertyName().equals(propertyName)) {
                return value;
            }
        }
        return null;
    }
}
