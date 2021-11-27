package config;

import java.util.HashMap;
import java.util.Map;

public class TestDao {
    private static final Map<String,String> map = new HashMap<>();

    static {
        map.put("test1","test1");
        map.put("test2","test2");
        map.put("test3","test3");
    }

    public void initMethod() {
        System.out.println("执行了TestDao中的initMethod方法");
    }

    public void destroyMethod() {
        System.out.println("执行了TestDao中的destroyMethod方法");
    }

    public void getMap() {
        System.out.println(map.values().stream());
    }
}
