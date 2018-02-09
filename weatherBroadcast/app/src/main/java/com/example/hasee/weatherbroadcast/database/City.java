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

    private int code;
    private String province;
    private String cityName;

    public void setCode(int code){
        this.code=code;
    }

    public int getCode(){
        return this.code;
    }

    public void setProvince(String province){
        this.province=province;
    }

    public String getProvince(){
        return this.province;
    }

    public void setCityName(String cityName){
        this.cityName=cityName;
    }

    public String getCityName(){
        return this.cityName;
    }
}
