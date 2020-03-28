package cn.iving.demo.common;

import android.util.Log;

/**
 * author：sheli
 * date：2020/3/28 19:48
 * description:Android 基本类的测试管理类
 */
public class TestManger {
    private static final String TAG ="TestManger";
    public static void test(){
        testBase64();
    }

    /**
     * Base64解码与译码
     */
    private static void testBase64(){
        Base64Demo base64Demo = new Base64Demo();
        String content="hello world!";
        String encodeReulst = base64Demo.encode(content);
        Log.d(TAG,"encodeReulst="+encodeReulst);
        String decodeResult =base64Demo.decode(encodeReulst);
        Log.d(TAG,"decodeResult="+decodeResult);
    }
}
