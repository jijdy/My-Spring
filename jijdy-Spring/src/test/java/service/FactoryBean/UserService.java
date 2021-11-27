package service.FactoryBean;

import lombok.Data;
import lombok.ToString;


public class UserService {

    private int id;

    private String name;

    private boolean delete;

    //相当于在Service层注入的dao实例
    private IUserDao userDao;

    public String printInfo() {
        //调用接口方法，相当于直接运行mybatis中xml中的sql代码，并在访问之后返回数值
        return userDao.printQuery(id);
    }
}
