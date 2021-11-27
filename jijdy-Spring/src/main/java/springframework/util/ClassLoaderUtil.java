package springframework.util;

public class ClassLoaderUtil {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Exception ignored) {}

        //如果未在当前线程上下文中找到类加载其，则直接将当前类的类加载器拿来运用
        if (null == cl) {
            cl = ClassLoaderUtil.class.getClassLoader();
        }

        return cl;
    }

    /**
     * @description 判断该类是否是一个cglib代理创建的类
     * @author 蒋遥/jijdy
     * @param: clazz
     * @createTime 2021/11/3 9:55
     * @return: boolean
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null) && isCglibProxyClassName(clazz.getName());
    }

    /**
     * @description 根据类名判断该类是否是通过cglib来进行代理创建的
     * @author 蒋遥/jijdy
     * @param: className
     * @createTime 2021/11/3 9:53
     * @return: boolean
     */
    public static boolean isCglibProxyClassName(String className) {
        return (className != null) && className.contains("$$");
    }

}
