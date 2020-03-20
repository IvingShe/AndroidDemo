package cn.iving.demo.customView;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;


import cn.iving.demo.androiddemoapp.R;

/**
 * @author Iving
 * @description Custom view and custom viewgroup demo
 * @date on 2019/6/10
 * reference：https://www.cnblogs.com/xiakexinghouzi/p/7843409.html
 **/


public class CustomViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_customview);
    }
}
