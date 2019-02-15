package cn.iving.demo.model;

/**
 * @author Iving
 * @description to do
 * @date on 2019/2/14
 **/
public interface IUser {

    String getUsername();

    String getPWD();

    boolean checkLoginVisible(String username, String pwd);

}
