package com.example.hasee.weatherbroadcast.app;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.bean.ForecastWeather;
import com.example.hasee.weatherbroadcast.bean.TodayWeather;

/**
 * Created by hasee on 2018/1/27.
 */

public class MyApplication {
    public static ForecastWeather forecastWeather[];        //未来四天的天气
    public static TodayWeather todayWeather = null;
    public static boolean isSelecting=false;
    public static boolean isAdding=false;
    public static void changeImg(View view){
        String type=todayWeather.getType();
        ImageView i=(ImageView) view.findViewById(R.id.weather_img);
        String updatetime="";
        updatetime=MyApplication.todayWeather.getUpdatetime();
        updatetime=updatetime.substring(0,updatetime.indexOf(":"));
        int nowTime=Integer.parseInt(updatetime);
        if(nowTime>=6&&nowTime<19){
            if(nowTime==18) view.findViewById(R.id.weather_today).setBackgroundResource(R.drawable.main_dusk);
            else view.findViewById(R.id.weather_today).setBackgroundResource(R.drawable.main_sun);
            switch(type){
                case "多云转晴":
                    i.setImageResource(R.drawable.cloudy_with_rain);
                    break;
                case "晴":
                    i.setImageResource(R.drawable.sun);
                    break;
                case "多云":
                    i.setImageResource(R.drawable.cloudy);
                    break;
                case "小雨":
                    i.setImageResource(R.drawable.small_rain);
                    break;
                case  "阴":
                    i.setImageResource(R.drawable.multycloudy);
                    break;
                case "阵雨":
                    i.setImageResource(R.drawable.shower);
                    break;
                case "雨夹雪":
                    i.setImageResource(R.drawable.rain_with_snow);
                    break;
                default:
            }
        }else{
            view.findViewById(R.id.weather_today).setBackgroundResource(R.drawable.main_night);
            switch(type){
                case "多云转晴":
                    i.setImageResource(R.drawable.cloudy_with_rain_night);
                    break;
                case "晴":
                    i.setImageResource(R.drawable.sun_night);
                    break;
                case "多云":
                    i.setImageResource(R.drawable.cloudy_night);
                    break;
                case "小雨":
                    i.setImageResource(R.drawable.small_rain);
                    break;
                case "阴":
                    i.setImageResource(R.drawable.multycloudy);
                    break;
                case "阵雨":
                    i.setImageResource(R.drawable.shower);
                    break;
                case "雨夹雪":
                    i.setImageResource(R.drawable.rain_with_snow);
                    break;
                default:
            }
        }
    }
}
