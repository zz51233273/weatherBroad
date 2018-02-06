package com.example.hasee.weatherbroadcast.bean;

/**
 * Created by hasee on 2018/2/6.
 */

public class ForecastWeather {
    private String city;
    private String high;
    private String low;
    private String type;
    private String fengli;
    private String date;

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getFengli() {
        return fengli;
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

    public void setCity(String city) {
        this.city = city;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
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

    public String toString() {
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ", fengli='" + fengli + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
