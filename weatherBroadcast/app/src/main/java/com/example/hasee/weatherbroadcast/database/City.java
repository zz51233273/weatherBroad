package com.example.hasee.weatherbroadcast.database;

/**
 * Created by hasee on 2018/1/28.
 */

public class City {
    public static final String TABLE="city";//表名
    public static final String KEY_ID = "_id";// 列名
    public static final String KEY_PROVINCE = "province";// 列名
    public static final String KEY_CITY = "city";// 列名
    public static final String KEY_CODE = "number";// 列名
    public static final String KEY_ALLPY = "allpy";// 列名
    public static final String KEY_ALLFIRSTPY = "allfirstpy";// 列名
    public static final String KEY_FIRSTPY= "firstpy";// 列名

    public int code;
    public String province;
    public String cityName;
}
