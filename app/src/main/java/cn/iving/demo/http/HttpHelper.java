package cn.iving.demo.http;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * author: li.she
 * date: 2020/5/8:20:01
 * description: 使用Http请求，向Android Server发送请求；
 *   需要serverOnAndoid 模块配置使用：
 *   注意需要配置 serverOnAndoid 机器上的IP和设置成serverOnAndoid配置的端口号；
 */
public class HttpHelper {

    private static String TAG="HttpHelper";
    private static String HOST="10.252.0.89";//配置成serverOnAndoid 机器上的IP
    private static String PORT="8080"; //配置成serverOnAndoid模块中的端口号
    private static String PATH = "http://"+HOST+":"+PORT;
   // private static String PATH = " https://www.baidu.com/";

    private static URL url;
    public HttpHelper() {
        // TODO Auto-generated constructor stub
    }

    static{
        try {
            url = new URL(PATH);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void init(){

    }

    public void sendReqeust() throws IOException {
        Log.d(TAG,"sendReqeust--begin");
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", "lili");
        params.put("password", "123");

        String encode ="utf-8";
        StringBuffer buffer = new StringBuffer();
        if(params != null&&!params.isEmpty()){
            //迭代器
            for(Map.Entry<String, String> entry : params.entrySet()){
                buffer.append(entry.getKey()).append("=").
                        append(URLEncoder.encode(entry.getValue(),encode)).
                        append("&");
            }
        }


        buffer.deleteCharAt(buffer.length()-1);
        byte[] mydata = buffer.toString().getBytes();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(3000);
        connection.setDoInput(true);//表示从服务器获取数据
        connection.setDoOutput(true);//表示向服务器写数据
        //获得上传信息的字节大小以及长度

        connection.setRequestMethod("POST");
        //是否使用缓存
        connection.setUseCaches(false);
        //表示设置请求体的类型是文本类型
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        connection.setRequestProperty("Content-Length", String.valueOf(mydata.length));
        connection.connect();   //连接，不写也可以。。？？有待了解

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(mydata,0,mydata.length);
        //获得服务器响应的结果和状态码
        int responseCode = connection.getResponseCode();
        Log.d(TAG,"sendReqeust--responseCode="+responseCode);
        if(responseCode == HttpURLConnection.HTTP_OK){
            //return changeInputeStream(connection.getInputStream(),encode);

        }

        Log.d(TAG,"sendReqeust--end");
    }


}
