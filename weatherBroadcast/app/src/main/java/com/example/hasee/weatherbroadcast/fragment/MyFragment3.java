package com.example.hasee.weatherbroadcast.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.app.MyApplication;
import com.example.hasee.weatherbroadcast.miniweather.MainActivity;


public class MyFragment3 extends Fragment implements View.OnClickListener{

    public MyFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_info_forecast, container, false);
        initTomorrowAf(view);
        return view;
    }

    public void initTomorrowAf(View view){       //更新后天的天气信息
        TextView t = (TextView) view.findViewById(R.id.city);
        t.setText(MyApplication.forecastWeather.getCity());
        t = (TextView) view.findViewById(R.id.week_today);
        t.setText(MyApplication.forecastWeather2.getDate());
        t = (TextView) view.findViewById(R.id.temperature);
        t.setText(MyApplication.forecastWeather2.getLow()+" ~ "+MyApplication.forecastWeather2.getHigh());
        t = (TextView) view.findViewById(R.id.climate);
        t.setText(MyApplication.forecastWeather2.getType());
        t = (TextView) view.findViewById(R.id.wind);
        t.setText("风力:"+MyApplication.forecastWeather2.getFengli());
        MyApplication.changeImg("6",MyApplication.forecastWeather2.getType(),view,2);
        ImageView cloth=(ImageView)view.findViewById(R.id.cloth);
        cloth.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.cloth){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext(),R.style.AlertDialogCustom);
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
