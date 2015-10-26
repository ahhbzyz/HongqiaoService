package com.fyp.hongqiaoservice.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fyp.hongqiaoservice.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Yaozhong on 2015/1/29.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final Context mContext;
    private static final int DATABASE_VERSION = 1;

    private String rootDirectory = "/data/data/com.fyp.hongqiaoservice/databases/";
    private static final String DATABASE_PATH = "/data/data/com.fyp.hongqiaoservice/databases/";
    private static  String DATABASE_NAME = null;

    private SQLiteDatabase mDataBase;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, Context mContext) {
        super(context, name, factory, version);
        this.mContext = mContext;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * */


    public void copyDatabase(String DATABASE_NAME) throws IOException {
        try
        {
            // 获得.db文件的绝对路径
            String databaseFilename = DATABASE_PATH + DATABASE_NAME;
            File dir = new File(rootDirectory);
            // 如果目录不存在，创建这个目录
            if (!dir.exists())
                dir.mkdir();
            // 如果在/data/data/org.itec.android.Classroom
            //目录中不存在 .db文件，则从res\raw目录中复制这个文件到该目录
            if (!(new File(databaseFilename)).exists()){
                // 获得封装.db文件的InputStream对象
                InputStream is =  mContext.getResources().openRawResource(R.raw.shuniu);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[1024];
                int count = 0;
                // 开始复制.db文件
                while ((count = is.read(buffer)) > 0){
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }

        }
        catch (Exception e){
        }
    }

    public SQLiteDatabase openDataBase(String DATABASE_NAME) throws SQLException {
        Log.d("open", "openDataBase");
        // Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
        mDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

        return mDataBase;
    }




    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();

    }

    public  String parseXML(String xml ){
        String result = xml.substring(xml.indexOf("<zh_cn>")+7,xml.indexOf("</zh_cn>"));
        return  result;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
