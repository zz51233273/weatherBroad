package com.example.hasee.weatherbroadcast.fragment;

import android.view.View;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.app.MyApplication;


public class MyFragment2 extends Fragment implements View.OnClickListener{

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
        MyApplication.changeImg(MyApplication.forecastWeather.getType(),view,1);
        ImageView cloth=(ImageView)view.findViewById(R.id.cloth);
        cloth.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){         //点击衣服图标，弹出穿衣推荐对话框
        if(view.getId()==R.id.cloth){
            AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext(),R.style.AlertDialogCustom);
            dialog.setTitle("穿衣推荐");
            dialog.setMessage(MyApplication.forecastWeather.getCloth());
            dialog.setCancelable(false);
            dialog.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }
    }
}
