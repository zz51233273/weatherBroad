package com.example.hasee.weatherbroadcast.app;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.bean.ForecastWeather;
import com.example.hasee.weatherbroadcast.bean.TodayWeather;
import com.example.hasee.weatherbroadcast.miniweather.MainActivity;

/**
 * Created by hasee on 2018/1/27.
 */

public class MyApplication {
    public static ForecastWeather forecastWeather=null; //明天
    public static ForecastWeather forecastWeather2=null; //后天
    public static TodayWeather todayWeather = null;
    public static ImageView weatherImg, pmImg;

    public static void changeImg(String updatetime,String type,View view){
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
                default:
            }
        }
    }
}
