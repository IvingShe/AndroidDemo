package cn.iving.demo.view;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.iving.demo.common.TestManger;
import cn.iving.demo.presenter.ILoginPresenter;
import cn.iving.demo.presenter.LoginPresenterImple;
import orc.migu.com.basemodulelibrary.Distributor;
import orc.migu.com.basemodulelibrary.ITaskDestribution;

/*
reference:
   https://www.cnblogs.com/changyiqiang/p/6044618.html
*/
public class LoginActivity extends AppCompatActivity implements ILoginView ,View.OnClickListener{

    private EditText etPwd, etUser;

    private Button btnLogin,btnClear;

    private ProgressBar progressBar;

    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        progressBar= this.findViewById(R.id.progressBar);
        etUser = (EditText) this.findViewById(R.id.et_username);
        etPwd = (EditText) this.findViewById(R.id.et_password);
        btnLogin= this.findViewById(R.id.btn_login);
        btnClear= this.findViewById(R.id.btn_clear);
        btnLogin.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        etPwd.setOnClickListener(this);
        etUser.setOnClickListener(this);

        loginPresenter= new LoginPresenterImple(this);
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);

    }

    @Override
    public void onClearText() {
        etUser.setText("");
        etPwd.setText("");
    }


    @Override
    public void onLoginResult(Boolean result, int code){
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);
        btnLogin.setEnabled(true);
        btnClear.setEnabled(true);
        if (result){
            Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Login Fail, code = " + code,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
      int id=v.getId();
      if(id == R.id.btn_login){
           //Distributor.turn2Acitivity(this,"View");
          // loginPresenter.setProgressBarVisiblity(View.VISIBLE);
           //loginPresenter.doLogin(etUser.getText().toString(),etPwd.getText().toString());

          TestManger.test();
      }else if(id == R.id.btn_clear){
           loginPresenter.clear();
      }
    }



}
