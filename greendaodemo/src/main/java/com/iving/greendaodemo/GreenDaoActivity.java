package com.iving.greendaodemo;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class GreenDaoActivity extends AppCompatActivity{

    private static final String TAG="GreenDaoActivity";
    private TextView mTvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        mTvContent =this.findViewById(R.id.tv_content);
        this.findViewById(R.id.tv_insert).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                insertData();
            }
        });
        this.findViewById(R.id.tv_create).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                createDB();
            }
        });

        this.findViewById(R.id.tv_query).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                qeuryDb();
            }
        });
    }


    private void  qeuryDb(){
        Log.d(TAG,"qeuryDb");
        if(mDaoManager==null){
            mDaoManager  = GreenDaoDemoManager.getInstance(this);
        }
        List<Person> persons= mDaoManager.query();
        if(persons!=null&&persons.size()>0){
            StringBuilder sb= new StringBuilder();
            for(Person p:persons){
                sb.append("id=").append(p.id)
                        .append(",name=").append(p.getUserName())
                        .append(",event=").append(p.PassWord)
                        .append("\n");
            }
            mTvContent.setText(sb.toString());
        }else{
            Toast.makeText(this,"数据库为空",Toast.LENGTH_SHORT).show();
        }


    }

    private  GreenDaoDemoManager mDaoManager;


    private void insertData(){
        Log.d(TAG,"insertData");
        if(mDaoManager==null){
            mDaoManager  = GreenDaoDemoManager.getInstance(this);
        }
        Person p= new Person(null,"luan","home");
        long id = mDaoManager.insert(p);
        Log.d(TAG,"id1="+id);
        p= new Person(null,"hefei","AnHuiUniversity");
        id = mDaoManager.insert(p);
        Log.d(TAG,"id2="+id);
        p= new Person(null,"chongqin","biaodian");
        id = mDaoManager.insert(p);
        Log.d(TAG,"id3="+id);
        p= new Person(null,"chengdu","Uninversity of Electronic");
        id = mDaoManager.insert(p);
        Log.d(TAG,"id4="+id);
        p= new Person(null,"shengzhen","ZTE");
        id =mDaoManager.insert(p);
        Log.d(TAG,"id5="+id);
        p= new Person(null,"nangjing","nubia");
        id =mDaoManager.insert(p);
        Log.d(TAG,"id6="+id);
        p= new Person(null,"hangzhou","Migu");
        id =mDaoManager.insert(p);
        Log.d(TAG,"id7="+id);

    }

    private void createDB(){
        Log.d(TAG,"createDB");
        if(mDaoManager==null){
            mDaoManager  = GreenDaoDemoManager.getInstance(this);
        }
    }


}
