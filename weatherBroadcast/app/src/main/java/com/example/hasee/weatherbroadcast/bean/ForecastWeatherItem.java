package com.example.hasee.weatherbroadcast.bean;

/**
 * Created by hasee on 2018/2/9.
 */

public class ForecastWeatherItem {
    private String week_today;
    private String temperature;
    private String climate;
    private int imgId;
    public ForecastWeatherItem(String week_today,String temperature,String climate,int imgId){
        this.week_today=week_today;
        this.temperature=temperature;
        this.climate=climate;
        this.imgId=imgId;
    }

    public String getWeek_today() {
        return week_today;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getClimate() {
        return climate;
    }

    public int getImgId() {
        return imgId;
    }
}
