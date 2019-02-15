package cn.iving.demo.view;

/**
 * @author Iving
 * @description to do
 * @date on 2019/2/14
 **/
public interface ILoginView {

    public void onClearText();

    public void onLoginResult(Boolean result, final int code);

    public void onSetProgressBarVisibility(int visibility);
}
