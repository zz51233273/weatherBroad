package com.example.hasee.weatherbroadcast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.bean.ForecastWeatherItem;
import com.example.hasee.weatherbroadcast.bean.IndexItem;

import java.util.List;

/**
 * Created by hasee on 2018/2/9.
 */

public class WeatherForecastAdapter extends ArrayAdapter<ForecastWeatherItem>{
    private int resourceId;
    public WeatherForecastAdapter(Context context, int textViewResourceId, List<ForecastWeatherItem> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ForecastWeatherItem forecastWeatherItem=getItem(position); // 获取当前项的forecastWeatherItem实例
        View view;
        ViewHolder2 viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder2();
            viewHolder.week_today = (TextView) view.findViewById (R.id.week_today_item);
            viewHolder.temperature = (TextView) view.findViewById (R.id.temperature_item);
            viewHolder.climate = (TextView) view.findViewById (R.id.climate_item);
            viewHolder.weatherImg=(ImageView)view.findViewById(R.id.weather_img_item);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder2) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.week_today.setText(forecastWeatherItem.getWeek_today());
        viewHolder.temperature.setText(forecastWeatherItem.getTemperature());
        viewHolder.climate.setText(forecastWeatherItem.getClimate());
        viewHolder.weatherImg.setImageResource(forecastWeatherItem.getImgId());
        return view;
    }
}
class ViewHolder2{
    TextView week_today;

    TextView temperature;

    TextView climate;

    ImageView weatherImg;
}