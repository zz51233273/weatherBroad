package com.example.hasee.weatherbroadcast.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    private static final int UPDATE_WEATHER_IMG = 1;
    private List<ForecastWeatherItem> forecastItemList = new ArrayList<ForecastWeatherItem>();
    private View view;
    public MyFragment3() {
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case UPDATE_WEATHER_IMG :
                    initForecastItem();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weather_broadcast_list, container, false);
        forecastItemList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg=new Message();
                msg.what=UPDATE_WEATHER_IMG;
                mHandler.sendMessage(msg);
            }
        }).start();
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
        WeatherForecastAdapter weatherForecastAdapter=new WeatherForecastAdapter(getContext(),R.layout.weatherforcast_item,forecastItemList);
        ListView listView=(ListView)view.findViewById(R.id.wListView);
        listView.setAdapter(weatherForecastAdapter);
    }

    private void changeImg(ForecastWeather forecastWeather){
        String type=forecastWeather.getType();
        switch(type){           //根据天气情况改变图片
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
            case "中雨":
                forecastWeather.setWeatherImg(R.drawable.middle_rain);
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
            case "小雪":
                forecastWeather.setWeatherImg(R.drawable.small_snow);
                break;
            case "中雪":
                forecastWeather.setWeatherImg(R.drawable.middle_snow);
                break;
            case "中到大雪":
                forecastWeather.setWeatherImg(R.drawable.middle_snow);
                break;
            case "大雪":
                forecastWeather.setWeatherImg(R.drawable.big_snow);
                break;
            case "阵雪":
                forecastWeather.setWeatherImg(R.drawable.multy_snow);
                break;
            default:
        }
    }
}
