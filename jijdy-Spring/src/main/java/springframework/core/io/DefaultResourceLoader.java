package springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/25 18:13
 * @Description 用于获取一个不确定的资源符，类似工厂模式，在一个方法中根据
 * 传入的资源来返回对应的资源类，并完成读取操作
 */
public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        Assert.notNull(location,"location must not be null!");
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        try {
            URL url = new URL(location);
            return new URLResource(url);
        } catch (MalformedURLException e) {
            return new FileSystemResource(location);
        }
    }
}
