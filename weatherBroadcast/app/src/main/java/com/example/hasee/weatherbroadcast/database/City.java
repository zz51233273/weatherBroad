package com.example.hasee.weatherbroadcast.database;

/**
 * Created by hasee on 2018/1/28.
 */

public class City {
    public static final String TABLE="city";//表名
    public static final String KEY_ID = "_id";// id
    public static final String KEY_PROVINCE = "province";// 省份
    public static final String KEY_CITY = "city";// 城市
    public static final String KEY_CODE = "number";// 城市代码
    public static final String KEY_FIRSTPY="firstpy";   //标记为是否为已添加城市

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
