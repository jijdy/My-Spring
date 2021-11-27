package springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class URLResource implements Resource{

    private final URL url;

    public URLResource(URL url) {
        Assert.notNull(url,"url must not be null!");
        this.url = url;
    }

    //直接使用url创建连接，并直接拿到url所在的资源，若失败并为http，则关闭资源通道并报出异常(减少资源消耗
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection connection = this.url.openConnection();
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            if (connection instanceof HttpURLConnection) {
                ((HttpURLConnection) connection).disconnect();
            }
            throw e;
        }
    }

}
