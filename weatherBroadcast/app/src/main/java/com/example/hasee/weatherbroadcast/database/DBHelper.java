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
    public void onCreate(SQLiteDatabase db) {       //建立数据库

    }




    public Cursor onQuery(SQLiteDatabase db, int id) {          //通过id查询
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

    public Cursor QueryCodeByProAndCity(SQLiteDatabase db,String province,String city){      //通过城市和省份查询城市代码
        Cursor cursor=null;
        String selectQuery =  "SELECT " +  City.KEY_CODE +
                " FROM " + City.TABLE + " where "+ City.KEY_PROVINCE + " = "+"\""+province+"\" and "+ City.KEY_CITY + " = "+"\""+city+"\"";
        cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor QueryCodeByCity(SQLiteDatabase db,String city){      //通过城市和标记查询城市代码
        Cursor cursor=null;
        String selectQuery =  "SELECT " +  City.KEY_CODE +
                " FROM " + City.TABLE + " where "+ City.KEY_CITY + " = "+"\""+city+"\" and "+ City.KEY_FIRSTPY + " = "+"\"1\"";
        Log.d("test",selectQuery);
        cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor QueryProvinceByCity(SQLiteDatabase db,String city){       //通过城市查询省份
        Cursor cursor=null;
        String selectQuery = "SELECT " + City.KEY_PROVINCE +
                " From " + City.TABLE + " where " + City.KEY_CITY + "=" + "\"" + city + "\"";
        cursor=db.rawQuery(selectQuery,null);
        return cursor;
    }

    public void updateYourCity(SQLiteDatabase db,String province,String city){       //更新您所添加的城市
        String updateQuery="UPDATE " + City.TABLE + " SET " + City.KEY_FIRSTPY + " = "+"\"1\" " + " WHERE " + City.KEY_PROVINCE + " = "+"\""+province+"\" and "+ City.KEY_CITY + " = "+"\""+city+"\"";
        db.execSQL(updateQuery);
    }

    public Cursor queryCityBySign(SQLiteDatabase db){    //通过标记来查找城市
        Cursor cursor=null;
        String selectQuery = "SELECT " + City.KEY_CITY +
                " From " + City.TABLE + " where " + City.KEY_FIRSTPY+ " = "+"\"1\"";
        cursor=db.rawQuery(selectQuery,null);
        return cursor;
    }

    public void deleteYourCity(SQLiteDatabase db,String city){       //删除您所添加的城市
        String updateQuery="update " + City.TABLE + " set " + City.KEY_FIRSTPY + " = " + "\"0\"" + " WHERE " + City.KEY_CITY + " = "+"\""+city+"\"";
        db.execSQL(updateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
