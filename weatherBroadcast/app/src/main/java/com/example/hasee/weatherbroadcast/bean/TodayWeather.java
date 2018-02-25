package com.example.hasee.weatherbroadcast.bean;

import android.provider.ContactsContract;
import android.widget.ImageView;

/**
 * Created by hasee on 2018/1/27.
 */

public class TodayWeather {
    private String city;
    private String updatetime;
    private String wendu;
    private String shidu;
    private String pm25;
    private String quality;
    private String fengxiang;
    private String fengli;
    private String date;
    private String high;
    private String low;
    private String type;
    private String index_comfort;            //舒适度
    private String index_cloth;              //穿衣指数
    private String index_influenza;         //感冒指数
    private String index_suncure;           //晾晒指数
    private String index_tour;              //旅游指数
    private String index_ultraviolet;      //紫外线指数
    private String index_sport;             //运动指数
    private String index_date;              //约会指数
    private ImageView weatherImg;

    public String getIndex_cloth() {
        return index_cloth;
    }

    public void setIndex_cloth(String index_cloth) {
        this.index_cloth = index_cloth;
    }

    public String getIndex_comfort() {
        return index_comfort;
    }

    public void setIndex_comfort(String index_comfort) {
        this.index_comfort = index_comfort;
    }

    public String getIndex_influenza() {
        return index_influenza;
    }

    public void setIndex_influenza(String index_influenza) {
        this.index_influenza = index_influenza;
    }

    public String getIndex_suncure() {
        return index_suncure;
    }

    public void setIndex_suncure(String index_suncure) {
        this.index_suncure = index_suncure;
    }

    public String getIndex_tour() {
        return index_tour;
    }

    public void setIndex_tour(String index_tour) {
        this.index_tour = index_tour;
    }

    public String getIndex_ultraviolet() {
        return index_ultraviolet;
    }

    public void setIndex_ultraviolet(String index_ultraviolet) {
        this.index_ultraviolet = index_ultraviolet;
    }

    public String getIndex_sport() {
        return index_sport;
    }

    public void setIndex_sport(String index_sport) {
        this.index_sport = index_sport;
    }

    public String getIndex_date() {
        return index_date;
    }

    public void setIndex_date(String index_date) {
        this.index_date = index_date;
    }

    public String getCity() {
        return city;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public String getWendu() {
        return wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public String getQuality() {
        return quality;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public String getDate() {
        return date;
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

    public ImageView getWeatherImg(){return weatherImg;}

    public void setCity(String city) {
        this.city = city;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public void setDate(String date) {
        this.date = date;
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

    public void setWeatherImg(ImageView weatherImg){this.weatherImg = weatherImg;}

    @Override
    public String toString() {
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", wendu='" + wendu + '\'' +
                ", shidu='" + shidu + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", quality='" + quality + '\'' +
                ", fengxiang='" + fengxiang + '\'' +
                ", fengli='" + fengli + '\'' +
                ", date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}