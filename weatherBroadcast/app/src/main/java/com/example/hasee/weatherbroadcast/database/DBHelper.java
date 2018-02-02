package com.example.hasee.weatherbroadcast.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hasee on 2018/1/28.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//建立数据库
/*        String CREATE_TABLE_CITY = "CREATE TABLE if not exists " + City.TABLE  + "("
                +City.KEY_ID  + " INTEGER PRIMARY KEY ,"
                +City.KEY_PROVINCE + " TEXT, "
                +City.KEY_CITY + " TEXT, "
                +City.KEY_CODE + " TEXT, "
                +City.KEY_ALLPY+" TEXT, "
                +City.KEY_ALLFIRSTPY + " TEXT, "
                +City.KEY_FIRSTPY +" TEXT )";
        db.execSQL(CREATE_TABLE_CITY);*/
    }




    public Cursor onQuery(SQLiteDatabase db, int id) {          //通过id查询学生
        Cursor cursor;
        String selectQuery =  "SELECT "  +  City.KEY_PROVINCE +
                " FROM " + City.TABLE + " where "+ City.KEY_ID + " = "+id;
        Log.d("test",selectQuery);
        cursor = db.rawQuery(selectQuery,null);
        return cursor;
    }
    public Cursor onQueryProvince(SQLiteDatabase db) {          //查询省份
        Cursor cursor;
        String selectQuery =  "SELECT "  +  City.KEY_PROVINCE +
                " FROM " + City.TABLE ;
        cursor = db.rawQuery(selectQuery,null);
        return cursor;
    }
    public Cursor QueryByProvince(SQLiteDatabase db, String province) {     //通过省查询城市
        Cursor cursor=null;
        String selectQuery =  "SELECT " +  City.KEY_CITY + "," + City.KEY_CODE+
                " FROM " + City.TABLE + " where "+ City.KEY_PROVINCE + " = "+"\""+province+"\"";
        cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }
    public Cursor QueryByCode(SQLiteDatabase db,String code){            //通过城市代码查询城市和省份
        Cursor cursor=null;
        String selectQuery =  "SELECT " +  City.KEY_PROVINCE + "," + City.KEY_CITY +
                " FROM " + City.TABLE + " where "+ City.KEY_CODE + " = "+"\""+code+"\"";
        cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
