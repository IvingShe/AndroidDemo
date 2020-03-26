package orc.migu.com.basemodulelibrary;

import android.content.Context;

/**
 * @author Iving
 * @description 模块间任务分发接口类:每个模块分发标志到对应的页面
 * @date on 2020/3/26
 * 参考文档：https://blog.csdn.net/cpcpcp123/article/details/103703525?
 * depth_1-utm_source=distribute.pc_relevant.none-task
 * &utm_source=distribute.pc_relevant.none-task
 **/

public interface ITaskDestribution {

    String TAG ="ITaskDestribution";

    void distribution(Context context, String flag, Object... objects);
}
