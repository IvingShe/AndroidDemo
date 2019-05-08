package cn.iving.demo.viewsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.iving.demo.viewsdemo.adapter.HangzhouAdapter;

/**
 * 2019,5,6
 */

public class CoordinaterLayoutActivity extends Activity {

    AppBarLayout mAppBarLayout;
    TextView mTvTitle;

    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinaterlayout);
        mTvTitle=findViewById(R.id.tvTitle);
        mAppBarLayout=findViewById(R.id.appbarLayout);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("iving","verticalOffset="+verticalOffset+",height="+appBarLayout.getMeasuredHeight());
                if(-verticalOffset>appBarLayout.getMeasuredHeight()/2){
                    mTvTitle.setVisibility(View.VISIBLE);
                }else{
                    mTvTitle.setVisibility(View.GONE);
                }
            }
        });

        initRecyclerView();

    }



    private void initRecyclerView(){
        mRecyclerView = this.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(this));

        List<String> data= new ArrayList<String>();
        for(int i=0;i<25;i++){
            data.add("杭州人民欢迎你");
        }
        mRecyclerView.setAdapter(new HangzhouAdapter(data));
    }
}
