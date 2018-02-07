package com.example.hasee.weatherbroadcast.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.app.MyApplication;


public class MyFragment1 extends Fragment {
    public MyFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_info, container, false);
        initToday(view);
        return view;
    }
    public void initToday(View view){
        TextView t = (TextView) view.findViewById(R.id.city);
        t.setText(MyApplication.todayWeather.getCity());
        t = (TextView) view.findViewById(R.id.time);
        t.setText(MyApplication.todayWeather.getUpdatetime()+ "发布");
        t = (TextView)view.findViewById(R.id.humidity);
        t.setText("湿度："+MyApplication.todayWeather.getShidu());
        t = (TextView) view.findViewById(R.id.week_today);
        t.setText(MyApplication.todayWeather.getDate());
        t = (TextView) view.findViewById(R.id.pm_data);
        t.setText(MyApplication.todayWeather.getPm25());
        t = (TextView) view.findViewById(R.id.pm2_5_quality);
        t.setText(MyApplication.todayWeather.getQuality());
        t = (TextView) view.findViewById(R.id.temperature);
        t.setText(MyApplication.todayWeather.getLow()+" ~ "+MyApplication.todayWeather.getHigh());
        t = (TextView) view.findViewById(R.id.climate);
        t.setText(MyApplication.todayWeather.getType());
        t = (TextView) view.findViewById(R.id.wind);
        t.setText("风力:"+MyApplication.todayWeather.getFengli());

        ImageView i=(ImageView) view.findViewById(R.id.pm2_5_img);
        i.setImageDrawable(MyApplication.todayWeather.getPmImg().getDrawable());
        i=(ImageView) view.findViewById(R.id.weather_img);
        i.setImageDrawable(MyApplication.todayWeather.getWeatherImg().getDrawable());
        String updatetime="";
        updatetime=MyApplication.todayWeather.getUpdatetime();
        updatetime=updatetime.substring(0,updatetime.indexOf(":"));
        MyApplication.changeImg(updatetime,MyApplication.todayWeather.getType(),view,0);
    }
}
