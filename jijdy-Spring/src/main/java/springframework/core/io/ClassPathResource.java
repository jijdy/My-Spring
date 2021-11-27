package springframework.core.io;

import cn.hutool.core.lang.Assert;
import springframework.util.ClassLoaderUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/25 16:57
 * @Description 通过传递到的路径来获得到输出流，避免新建文件对象占有资源
 * 使用线程上下文中的类加载器类直接从path中拿到输入流得到资源
 */
public class ClassPathResource implements Resource{

    private final String path;

    private final ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path,null);
    }

    //创建时直接拿到对应的类加载器，从线程上下文中获取
    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path,"path must not be null!");
        this.path = path;
        this.classLoader = classLoader != null ? classLoader : ClassLoaderUtil.getDefaultClassLoader();
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        InputStream resourceAsStream = classLoader.getResourceAsStream(path);
        if (null == resourceAsStream) {
            throw new FileNotFoundException("file is no exist!");
        }
        return resourceAsStream;
    }
}
