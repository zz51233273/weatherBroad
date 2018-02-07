package com.example.hasee.weatherbroadcast.app;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.bean.ForecastWeather;
import com.example.hasee.weatherbroadcast.bean.TodayWeather;

/**
 * Created by hasee on 2018/1/27.
 */

public class MyApplication {
    public static ForecastWeather forecastWeather=null; //明天
    public static ForecastWeather forecastWeather2=null; //后天
    public static TodayWeather todayWeather = null;

    public static void changeImg(String updatetime,String type,View view,int pos){
        ImageView i=(ImageView) view.findViewById(R.id.weather_img);
        int nowTime=Integer.parseInt(updatetime);
        if(nowTime>=6&&nowTime<19){
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
                default:
            }
        }else{
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
                case  "阴":
                    i.setImageResource(R.drawable.multycloudy);
                    break;
                default:
            }
        }
        if(pos==1){
            forecastWeather.setWeatherImg(i);
        }else if(pos==2){
            forecastWeather2.setWeatherImg(i);
        }
    }
}
