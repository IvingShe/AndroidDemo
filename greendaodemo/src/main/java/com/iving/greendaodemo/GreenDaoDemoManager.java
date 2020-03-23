package com.iving.greendaodemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.iving.greendao.gen.DaoMaster;
import com.iving.greendao.gen.DaoSession;
import com.iving.greendao.gen.PersonDao;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

/**
 * @author Iving
 * @description GreenDao Demo Manager:
 *             演示对Person对象数据库的操作
 * @date on 2020/3/23
 **/
public class GreenDaoDemoManager {

    private static final String TAG ="GreenDaoDemoManager";

    private static final String DB_NAME="person.db";
    private static GreenDaoDemoManager sManager = null;

    private Context mContext;

    public static GreenDaoDemoManager getInstance(@NotNull Context context) {
        if (sManager == null) {
            sManager = new GreenDaoDemoManager(context);
        }
        return sManager;
    }

    private GreenDaoDemoManager(@NotNull Context context) {
        mContext=context;
        initDataBase(mContext);
    }


    private DaoMaster mDaoMaster;
    private DaoMaster.DevOpenHelper mDevHelper;
    private DaoSession mDaoSession;
    private PersonDao mPersonDao;
    private void initDataBase(Context context){
        mDevHelper= new DaoMaster.DevOpenHelper(context,DB_NAME,null);
        mDaoMaster =new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        mPersonDao = mDaoSession.getPersonDao();
    }

    /**
     * 获取可写数据库
     * @return
     */
    public SQLiteDatabase getWritableDatabase(){
        if(mDevHelper==null){
            mDevHelper= new DaoMaster.DevOpenHelper(mContext,DB_NAME,null);
        }
        SQLiteDatabase db = mDevHelper.getWritableDatabase();
        return  db;
    }


    /**
     * 获取可读数据库
     * @return
     */
    public SQLiteDatabase getReadableDatabase(){
        if(mDevHelper==null){
            mDevHelper= new DaoMaster.DevOpenHelper(mContext,DB_NAME,null);
        }
        SQLiteDatabase db = mDevHelper.getReadableDatabase();
        return db;
    }


    /**
     * 向数据库的数据表中插入数据；
     * @param person
     * @return
     */
    public  long insert(Person person ){
       long id =  mPersonDao.insert(person);
       return  id;
    }

    /**
     * 在数据库的数据表查询所有数据；
     * @return
     */
    public List<Person> query(){
        return mPersonDao.queryBuilder().list();
    }



}
