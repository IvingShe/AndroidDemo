package cn.iving.demo.viewsdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;


/**
 * @author Iving
 * @description to do
 * @date on 2019/3/8
 **/
public class RecyclerViewDemoActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerviewdemo_main);
        mRecyclerView =findViewById(R.id.recyclerView);
    }
}
