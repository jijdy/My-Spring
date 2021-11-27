package springframework.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/25 16:38
 * @Description 用户spring加载配置类的接口，提供得到输入流的抽象方法
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
}
