package cn.iving.demo;

import android.util.Log;

import com.example.thread.CallabeDemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Iving
 * @description to do
 * @date on 2020/3/11
 **/
public class JavaUtils {


    private static final String TAG="JavaUtils";


    public void testPattern(){
       Pattern p=Pattern.compile("\\w+");
        String strPattern= p.pattern();
        Log.d(TAG,"strPattern="+strPattern);
        p=Pattern.compile("\\d+");
        String[] str=p.split("我的QQ是:456456我的电话是:0532214我的邮箱是:aaa@aaa.com");
        for(String s:str){
            Log.d(TAG,"s="+s);
        }
        //
        p=Pattern.compile("\\[[\\u4e00-\\u9fa5]+\\]");
        strPattern= p.pattern();
        Log.d(TAG,"strPattern="+strPattern);
        boolean match=Pattern.matches("\\d+","2223");//返回true
        Pattern.matches("\\d+","2223aa");//返回false,需要匹配到所有字符串才能返回true,这里aa不能匹配到
        Pattern.matches("\\d+","22bb23");//返回false,需要匹配到所有字符串才能返回true,这里bb不能匹配到

        p=Pattern.compile("\\d+");
        Matcher m=p.matcher("22bb23");
        Pattern p2= m.pattern();
        Log.d(TAG,"p2 Pattern="+p2.pattern());

        p=Pattern.compile("\\d+");
        m=p.matcher("22bb23");
        boolean flag= m.matches();//返回false,因为bb不能被\d+匹配,导致整个字符串匹配未成功.
        Log.d(TAG,"flag="+flag);

        Matcher m2=p.matcher("2223");
        flag =m2.matches();//返回true,因为\d+匹配到了整个字符串
        Log.d(TAG,"flag="+flag);

        p=Pattern.compile("\\d+");
        m=p.matcher("22bb23");
        boolean find=  m.find();//返回true
        Log.d(TAG,"find1="+find);
        m2=p.matcher("aa2223");
        find=  m2.find();//返回true
        Log.d(TAG,"find2="+find);
        Matcher m3=p.matcher("aa2223bb");
        find=m3.find();//返回true
        Log.d(TAG,"find3="+find);
        Matcher m4=p.matcher("aabb");
        find=m4.find();//返回false
        Log.d(TAG,"find4="+find);


        p=Pattern.compile("\\d");
        m=p.matcher("aaa2423bb332233");
        find= m.find();//匹配2223
        Log.d(TAG,"find5="+find);
        int start=m.start();//返回3
        int end =m.end();//返回7,返回的是2223后的索引号
        Log.d(TAG,"start="+start+",end="+end);
        String group=m.group();//返回2223
        Log.d(TAG,"group="+group);
        int groupCount =m.groupCount();
        p=Pattern.compile("\\d+");
        m=p.matcher("我的QQ是:456456 我的电话是:0532214 我的邮箱是:aaa123@aaa.com");
        while(m.find()) {
            Log.d(TAG,"group1="+m.group());
        }

    }


    public void testCallableDemo(){
     CallabeDemo callabeDemo = new  CallabeDemo();
     callabeDemo.submitTask();
    }
}
