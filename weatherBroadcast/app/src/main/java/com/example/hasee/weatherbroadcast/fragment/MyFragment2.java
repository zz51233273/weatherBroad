package com.example.hasee.weatherbroadcast.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.app.MyApplication;


public class MyFragment2 extends Fragment {

    public MyFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_info_forecast, container, false);
        initTomorrow(view);
        return view;
    }

    public void initTomorrow(View view){         //更新明天的天气信息
        TextView t = (TextView) view.findViewById(R.id.city);
        t.setText(MyApplication.forecastWeather.getCity());
        t = (TextView) view.findViewById(R.id.week_today);
        t.setText(MyApplication.forecastWeather.getDate());
        t = (TextView) view.findViewById(R.id.temperature);
        t.setText(MyApplication.forecastWeather.getLow()+" ~ "+MyApplication.forecastWeather.getHigh());
        t = (TextView) view.findViewById(R.id.climate);
        t.setText(MyApplication.forecastWeather.getType());
        t = (TextView) view.findViewById(R.id.wind);
        t.setText("风力:"+MyApplication.forecastWeather.getFengli());
        MyApplication.changeImg("6",MyApplication.forecastWeather.getType(),view,1);
    }
}
