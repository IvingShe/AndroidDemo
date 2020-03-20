package cn.iving.demo.androiddemoapp;

import android.content.Context;
import android.content.res.AssetManager;

import com.neusoft.track.utils.NLog;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Iving
 * @description test download to /data/data/
 * @date on 2019/7/1
 **/
public class DownloadDemo extends  Thread{


    private Context context;

    public DownloadDemo(Context context) {
        this.context = context;

    }


    private void getAssets(){
        try {
           AssetManager am =context.getAssets();
           InputStream is= am.open("test.mp3");
           NLog.d("iving","getAssets2");
           wirteFile(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void wirteFile(InputStream inputStream){
        try {
           FileOutputStream fos= context.openFileOutput("test_mm.mp3",Context.MODE_PRIVATE);
            NLog.d("iving","wirteFile1");
            byte[] bytes = new byte[2048];
            //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
            int n = -1;
            //循环取出数据
            while ((n = inputStream.read(bytes,0,bytes.length)) != -1) {
                fos.write(bytes, 0, n);
            }
            //关闭流
            inputStream.close();
            fos.close();
            NLog.d("iving","wirteFile2");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        super.run();
        getAssets();
    }
}
