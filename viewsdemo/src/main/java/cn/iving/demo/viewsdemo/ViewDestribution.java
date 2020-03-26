package cn.iving.demo.viewsdemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import orc.migu.com.basemodulelibrary.ITaskDestribution;

/**
 * @author Iving
 * @description to do
 * @date on 2020/3/26
 **/
public class ViewDestribution implements ITaskDestribution {
    @Override
    public void distribution(Context context, String flag, Object... objects) {
           Log.d(TAG,"---ViewDestribution---");
           Intent intent = new Intent();
           intent.setClass(context,RecyclerViewDemoActivity.class);
           context.startActivity(intent);
    }
}
