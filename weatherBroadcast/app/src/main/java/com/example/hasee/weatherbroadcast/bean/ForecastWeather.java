package com.example.hasee.weatherbroadcast.bean;

import android.widget.ImageView;

/**
 * Created by hasee on 2018/2/6.
 */

public class ForecastWeather {
    private String city;
    private String high;
    private String low;
    private String type;
    private String date;
    private int weatherImg;

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getType() {
        return type;
    }

    public int getWeatherImg(){return weatherImg;}

    public void setCity(String city) {
        this.city = city;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeatherImg(int weatherImg){this.weatherImg=weatherImg;}

    public String toString() {
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
