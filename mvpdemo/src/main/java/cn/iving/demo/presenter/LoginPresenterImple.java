package cn.iving.demo.presenter;

import android.os.Handler;
import android.os.Looper;

import cn.iving.demo.model.IUser;
import cn.iving.demo.model.UserModel;
import cn.iving.demo.view.ILoginView;

/**
 * @author Iving
 * @description to do
 * @date on 2019/2/14
 **/
public class LoginPresenterImple implements ILoginPresenter {

    ILoginView iLoginView;
    IUser user;
    Handler handler;

    public LoginPresenterImple(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * UI logic
     */
    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    /**
     * business logic
     * @param name
     * @param passwd
     */
    @Override
    public void doLogin(String name, String passwd) {
        final boolean result = user.checkLoginVisible(name,passwd);
        final int code;
        if(result){
            code = 200;
        }else{
            code = -1;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code);
            }
        }, 3000);

    }

    /**
     * UI logic
     */
    @Override
    public void setProgressBarVisiblity(int visiblity){
        iLoginView.onSetProgressBarVisibility(visiblity);
    }

    private void initUser(){
        user = new UserModel("mvp","mvp");
    }
}
