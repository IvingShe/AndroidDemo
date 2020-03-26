package cn.iving.demo;

import android.content.Context;
import android.util.Log;

import orc.migu.com.basemodulelibrary.ITaskDestribution;

/**
 * @author Iving
 * @description MVP模式的任务分发类
 * @date on 2020/3/26
 **/
public class MVPTaskDestribution implements ITaskDestribution {
    @Override
    public void distribution(Context context, String flag, Object... objects) {
        Log.d(TAG,"--MVPTaskDestribution--");
    }
}
