package com.example.futuretask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //https://www.jianshu.com/p/9f4229cca027

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private TextView mTvFutureAndCallable;
    private TextView mTvFutureTask;
  private void  initUI(){
      Button button = findViewById(R.id.btn_future_callable);
      button.setOnClickListener(this);
      mTvFutureAndCallable = findViewById(R.id.tv_future);

      Button futureTaskButton = findViewById(R.id.btn_FutureTask);
      futureTaskButton.setOnClickListener(this);
      mTvFutureTask = findViewById(R.id.tv_FutureTask);

  }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_future_callable:
                FutureAndCallableTest test = new FutureAndCallableTest();
                String result =test.consumerTime();
                mTvFutureAndCallable.setText(result);
                break;
            case R.id.btn_FutureTask:
                FutureTaskAndCallableTest test2 = new FutureTaskAndCallableTest();
                String result2 =test2.cusmterTime();
                mTvFutureTask.setText(result2);
                break;
        }
    }
}

