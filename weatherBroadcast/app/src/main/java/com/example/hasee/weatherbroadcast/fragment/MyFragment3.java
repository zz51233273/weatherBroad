package com.example.hasee.weatherbroadcast.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlertDialog;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.adapter.WeatherForecastAdapter;
import com.example.hasee.weatherbroadcast.app.MyApplication;
import com.example.hasee.weatherbroadcast.bean.ForecastWeather;
import com.example.hasee.weatherbroadcast.bean.ForecastWeatherItem;

import java.util.ArrayList;
import java.util.List;


public class MyFragment3 extends Fragment{

    private List<ForecastWeatherItem> forecastItemList = new ArrayList<ForecastWeatherItem>();
    public MyFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_broadcast_list, container, false);
        forecastItemList.clear();
        initForecastItem();
        WeatherForecastAdapter weatherForecastAdapter=new WeatherForecastAdapter(getContext(),R.layout.weatherforcast_item,forecastItemList);
        ListView listView=(ListView)view.findViewById(R.id.wListView);
        listView.setAdapter(weatherForecastAdapter);
        return view;
    }

    private void initForecastItem(){
        ForecastWeatherItem forecastWeatherItem=null;
        int pos=MyApplication.forecastWeather.length;
        for (int i=0;i<pos;i++){
            changeImg(MyApplication.forecastWeather[i]);
            forecastWeatherItem=new ForecastWeatherItem(MyApplication.forecastWeather[i].getDate(),
                    MyApplication.forecastWeather[i].getLow()+" ~ "+MyApplication.forecastWeather[i].getHigh()
                    , MyApplication.forecastWeather[i].getType()
                    , MyApplication.forecastWeather[i].getWeatherImg());
            forecastItemList.add(forecastWeatherItem);
        }
    }
    public void changeImg(ForecastWeather forecastWeather){
        String updatetime="";
        updatetime=MyApplication.todayWeather.getUpdatetime();
        updatetime=updatetime.substring(0,updatetime.indexOf(":"));
        int nowTime=Integer.parseInt(updatetime);
        String type=forecastWeather.getType();
        if(nowTime>=6&&nowTime<19){
            switch(type){
                case "多云转晴":
                    forecastWeather.setWeatherImg(R.drawable.cloudy_with_rain);
                    break;
                case "晴":
                    forecastWeather.setWeatherImg(R.drawable.sun);
                    break;
                case "多云":
                    forecastWeather.setWeatherImg(R.drawable.cloudy);
                    break;
                case "小雨":
                    forecastWeather.setWeatherImg(R.drawable.small_rain);
                    break;
                case  "阴":
                    forecastWeather.setWeatherImg(R.drawable.multycloudy);
                    break;
                case "阵雨":
                    forecastWeather.setWeatherImg(R.drawable.shower);
                    break;
                case "雨夹雪":
                    forecastWeather.setWeatherImg(R.drawable.rain_with_snow);
                    break;
                default:
            }
        }else{
            switch(type){
                case "多云转晴":
                    forecastWeather.setWeatherImg(R.drawable.cloudy_with_rain_night);
                    break;
                case "晴":
                    forecastWeather.setWeatherImg(R.drawable.sun_night);
                    break;
                case "多云":
                    forecastWeather.setWeatherImg(R.drawable.cloudy_night);
                    break;
                case "小雨":
                    forecastWeather.setWeatherImg(R.drawable.small_rain);
                    break;
                case "阴":
                    forecastWeather.setWeatherImg(R.drawable.multycloudy);
                    break;
                case "阵雨":
                    forecastWeather.setWeatherImg(R.drawable.shower);
                    break;
                case "雨夹雪":
                    forecastWeather.setWeatherImg(R.drawable.rain_with_snow);
                    break;
                default:
            }
        }
    }
}
