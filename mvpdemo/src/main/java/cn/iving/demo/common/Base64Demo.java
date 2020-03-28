package com.demo.base64;





/**
 * author：sheli
 * date：2020/3/28 19:11
 * description:
 *   演示Base64的Demo
 *
 */
public class Base64Demo {


    private  String content;

    /**
     *
     * @param content
     * @return 基于base64的译码输出
     */
    public String encode(String content){

        String result=null;
        result = Base64.encode(content.getBytes());

        return  result;
    }

    public String decode(String content){
        String decodeResult=null;
        try {
            byte[] bytes = Base64.decode(content);
            decodeResult = new String(bytes);
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        }
        return  decodeResult;
    }
}
