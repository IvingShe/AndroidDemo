package cn.iving.demo.common;


import android.util.Base64;

/**
 * author：sheli
 * date：2020/3/28 19:11
 * description:
 * 演示Base64的Demo
 */
public class Base64Demo {
    /**
     * @param content
     * @return 基于base64的译码输出
     */
    public String encode(String content) {
        String result = null;
        result = Base64.encodeToString(content.getBytes(), Base64.DEFAULT);
        return result;
    }
    /**
     * @param content
     * @return 基于base64的解码输出
     */
    public String decode(String content) {
        String decodeResult = null;
        byte[] bytes = Base64.decode(content.getBytes(), Base64.DEFAULT);
        decodeResult = new String(bytes);
        return decodeResult;
    }
}
