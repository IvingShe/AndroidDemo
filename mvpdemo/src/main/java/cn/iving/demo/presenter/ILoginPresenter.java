package cn.iving.demo.presenter;

/**
 * @author Iving
 * @description to do
 * @date on 2019/2/14
 **/
public interface ILoginPresenter {
    //UI logic
    void clear();
    void setProgressBarVisiblity(int visiblity);

    //Business Logic
    void doLogin(String name, String passwd);
}
