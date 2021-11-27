package service.beanpostprocessor;

import cn.hutool.core.bean.BeanException;
import springframework.beans.PropertyValue;
import springframework.beans.PropertyValues;
import springframework.beans.factory.ConfigurableListBeanFactory;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanFactoryPostProcessor;
import springframework.beans.factory.config.ConfigurableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListBeanFactory beanFactory) throws BeanException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        //根据bean定义得到对于bean的字段属性，以便对其进行修改操作
        PropertyValues fieldValue = beanDefinition.getFieldValue();

        fieldValue.addPropertyValue(new PropertyValue("string","这是更改之后的数值；"));
    }
}
