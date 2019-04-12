package cn.iving.demo.androiddemoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cn.iving.demo.view.LoginActivity;
import cn.iving.demo.viewsdemo.RecyclerViewDemoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        this.findViewById(R.id.btn_mvp).setOnClickListener(this);
        //btn_customView
        this.findViewById(R.id.btn_customView).setOnClickListener(this);
        this.findViewById(R.id.btn_permission).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mvp:
                Toast.makeText(this, "进入MVP demo", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);

                break;

            case R.id.btn_customView:
                Intent intent2 = new Intent(this, RecyclerViewDemoActivity.class);
                this.startActivity(intent2);

                break;
            case R.id.btn_permission:
//                Intent intent3 = new Intent(this, PermissionCheckerActivity.class);
////                this.startActivity(intent3);
                jump2Log();
                break;

        }
    }



    private void jump2Log(){
        Intent intent3 = new Intent(this, com.neusoft.track.MainActivity.class);
        this.startActivity(intent3);
    }
}
