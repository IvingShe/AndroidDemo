package com.zhejianglab.serveronandroid;

import android.util.Log;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.request.Method;
import org.nanohttpd.protocols.http.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.nanohttpd.protocols.http.response.Response.newFixedLengthResponse;

/**
 * author: li.she
 * date: 2020/5/8:19:46
 * description:Server的HttpServer接收端；
 */
public class Http extends NanoHTTPD {

    private String TAG ="Http";

    public Http(int port) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Log.i(TAG, "serve: ");//如果有uri,会打印出uri

        try {
            // 这一句话必须要写，否则在获取数据时，获取不到数据
            session.parseBody(new HashMap<String, String>());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder();
        Method method = session.getMethod();
        String uri = session.getUri();
        Map<String, String> parms = session.getParms();
        String data = parms.get("data");//这里的data是POST提交表单时key
        Log.i(TAG, "uri: "+uri);//如果有uri,会打印出uri
        Log.i(TAG, "data: "+data);
        Log.i(TAG, "method: "+method.toString());
        getHeaders(session.getHeaders());
        builder.append("任意内容");// 反馈给调用者的数据
        return newFixedLengthResponse(builder.toString());
    }

    private void getHeaders(Map<String, String> headers){
        StringBuilder sb = new StringBuilder();
        for(String key:headers.keySet()){
            sb.append("key:").append(key).append(",value:").append(headers.get(key));
        }
        Log.i(TAG, "getHeaders: "+sb);
    }

}
