package com.example.hasee.weatherbroadcast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.bean.IndexItem;

import java.util.List;

/**
 * Created by hasee on 2018/2/9.
 */

public class IndexAdapter extends ArrayAdapter<IndexItem>{

    private int resourceId;
    public IndexAdapter(Context context,int textViewResourceId,List<IndexItem> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IndexItem indexItem = getItem(position); // 获取当前项的index实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.recommendImg = (ImageView) view.findViewById (R.id.recommend_image);
            viewHolder.indexName = (TextView) view.findViewById (R.id.index_name);
            viewHolder.indexValue = (TextView) view.findViewById (R.id.index_value);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.recommendImg.setImageResource(indexItem.getImgId());
        viewHolder.indexName.setText(indexItem.getName());
        viewHolder.indexValue.setText(indexItem.getValue());
        return view;
    }
}


class ViewHolder{
    ImageView recommendImg;

    TextView indexName;

    TextView indexValue;
}