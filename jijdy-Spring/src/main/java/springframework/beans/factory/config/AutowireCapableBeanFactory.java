package springframework.beans.factory.config;

import cn.hutool.core.bean.BeanException;
import springframework.beans.factory.BeanFactory;

//自动化处理Bean工厂配置的接口
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeanException;


    Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeanException;


}
