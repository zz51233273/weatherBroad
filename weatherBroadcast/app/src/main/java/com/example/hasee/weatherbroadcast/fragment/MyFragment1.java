package com.example.hasee.weatherbroadcast.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.app.MyApplication;


public class MyFragment1 extends Fragment{
    private static final int UPDATE_WEATHER_IMG = 1;
    private View view;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case UPDATE_WEATHER_IMG :
                    initImg((View)msg.obj);
                    MyApplication.changeImg((View)msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    public MyFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weather_info, container, false);
        initToday(view);
        return view;
    }

    public void initToday(final View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg=new Message();
                msg.what=UPDATE_WEATHER_IMG;
                msg.obj=view;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    void initImg(View view){
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
        int pm=0;
        if(null!=MyApplication.todayWeather.getPm25())
            pm=Integer.parseInt(MyApplication.todayWeather.getPm25());
        if(pm<=50){
            i.setImageResource(R.drawable.biz_plugin_weather_0_50);
        }else if(pm<=100){
            i.setImageResource(R.drawable.biz_plugin_weather_51_100);
        }else if(pm<=150){
            i.setImageResource(R.drawable.biz_plugin_weather_101_150);
        }else if(pm<=200){
            i.setImageResource(R.drawable.biz_plugin_weather_151_200);
        }else if(pm<=300){
            i.setImageResource(R.drawable.biz_plugin_weather_201_300);
        }else{
            i.setImageResource(R.drawable.biz_plugin_weather_greater_300);
        }
        i=(ImageView) view.findViewById(R.id.weather_img);
        if(null!=MyApplication.todayWeather.getWeatherImg())
            i.setImageDrawable(MyApplication.todayWeather.getWeatherImg().getDrawable());
    }

}
