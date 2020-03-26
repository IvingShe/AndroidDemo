package orc.migu.com.basemodulelibrary;

import android.content.Context;

import java.util.HashMap;

/**
 * @author Iving
 * @description to do
 * @date on 2020/3/26
 **/
public class Distributor {

    private static HashMap<String, String> hashMap = new HashMap<>();
    private static ITaskDestribution taskDistribution;

    static {
        hashMap.put("Login", "cn.iving.demo.MVPTaskDestribution");
        hashMap.put("View", "cn.iving.demo.viewsdemo.ViewDestribution");
    }

    private static void getTaskDistribution(String flag) {
        try {
            Class c = null;
            if (flag != null && flag.startsWith("Login")) {
                c = Class.forName(hashMap.get("Login"));
            }
            if (flag != null && flag.startsWith("View")) {
                c = Class.forName(hashMap.get("View"));
            }
            taskDistribution = (ITaskDestribution) c.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void turn2Acitivity(Context context, String flag, Object... objects) {
        getTaskDistribution(flag);
        taskDistribution.distribution(context, flag, objects);
    }
}
