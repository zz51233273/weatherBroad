package com.example.hasee.weatherbroadcast.database;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.hasee.weatherbroadcast.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class DBManager {
    public static final String DB_NAME = "city.db"; //数据库名字
    public static final String PACKAGE_NAME ="com.example.hasee.weatherbroadcast";//包名
    public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;   //数据库的绝对路径( /data/data/com.*.*(package name))
    private Context context;

    public DBManager(Context context) {
        this.context = context;
    }

    //对外提供的写入数据接口
    public void writeData() {
        this.writeData(DB_PATH + "/databases");
    }

    // 写入数据方法
    private void writeData(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) { //判断文件是否存在
                //通过输入流和输出流，把数据库拷贝到"filePath"下
                file.mkdir();
                File file2=new File(filePath+"/"+DB_NAME);
                if (!file2.exists()) {
                    InputStream is = context.getResources().openRawResource(R.raw.city);//获取输入流，使用R.raw.test资源
                    FileOutputStream fos = new FileOutputStream(file2);
                    byte[] buffer = new byte[1024];
                    int readCount;
                    while ((readCount = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, readCount);
                    }
                    fos.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}