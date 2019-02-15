package cn.iving.demo.model;

/**
 * @author Iving
 * @description to do
 * @date on 2019/2/14
 **/
public class UserModel implements IUser {

    private String username;
    private String pwd;

    public UserModel(String username, String pwd) {
        this.username = username;
        this.pwd = pwd;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPWD() {
        return pwd;
    }

    @Override
    public boolean checkLoginVisible(String username, String pwd) {
        if (this.username.equals(username) && this.pwd.equals(pwd)) {
            return true;
        }
        return false;
    }
}
