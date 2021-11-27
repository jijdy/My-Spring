package springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.io.*;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/25 16:39
 * @Description 主要是本地系统文件的加载读取，通过直接传入一个文件类，
 * 或传入一个文件所在的地址path来直接拿到该本地问价并获取到对应的输出流
 * 注意：使用文件进行输出流操作时，直接将文件对象赋值给到文件流对象，并直接对文件
 * 流对象进行操作即可
 */
public class FileSystemResource implements Resource{

    private File file;

    private final String path;

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        Assert.notNull(path,"file path must not be null!");
        this.file = new File(path);
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(this.file);
    }
}
