package springframework.context.support;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/28 10:33
 * @Description 用于存储bean配置资源的位置，并将其作为实现类供用户使用
 */
public class ClassPathXMLApplicationContext extends AbstractXmlApplicationContext{

    private String[] configLocations;

    public ClassPathXMLApplicationContext(){}

    public ClassPathXMLApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    //供给用户调用的方法，上下文实例化就会直接刷新bean容器和其配置
    public ClassPathXMLApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }

}
