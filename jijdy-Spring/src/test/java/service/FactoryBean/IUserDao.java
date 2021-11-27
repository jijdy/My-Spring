package service.FactoryBean;

public interface IUserDao {

    //方法需要和具体需要实现的代码进行映射，mybatis中一般使用xml，则会直接对应sql语句
    String printQuery(int id);
}
