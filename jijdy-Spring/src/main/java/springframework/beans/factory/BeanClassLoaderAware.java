package springframework.beans.factory;

import cn.hutool.core.bean.BeanException;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/30 20:26
 * @Description  能够通过继承该接口感知到bean自身所属的ClassLoader对象
 */
public interface BeanClassLoaderAware extends Aware{

    void setBeanClassLoader(ClassLoader classLoader) throws BeanException;
}
