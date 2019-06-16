package cn.iving.demo.androiddemoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;



import cn.iving.demo.annotation.ViewInject;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewInject annotation = this.getClass().getAnnotation(ViewInject.class);
        if(annotation!=null){
            if(annotation.mainLayoutId()>0){
                setContentView(annotation.mainLayoutId());
            }else{
                throw new RuntimeException("annotation <0");
            }
        }else{
            throw new RuntimeException("annotation==null");
        }
    }
}
