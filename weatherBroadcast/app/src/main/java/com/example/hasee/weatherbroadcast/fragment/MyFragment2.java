package com.example.hasee.weatherbroadcast.fragment;

import android.view.View;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.adapter.IndexAdapter;
import com.example.hasee.weatherbroadcast.app.MyApplication;
import com.example.hasee.weatherbroadcast.bean.IndexItem;

import java.util.ArrayList;
import java.util.List;


public class MyFragment2 extends Fragment{

    private List<IndexItem> indexItemList = new ArrayList<IndexItem>();
    public MyFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_index_list, container, false);
        indexItemList.clear();
        initIndex();
        IndexAdapter indexAdapter=new IndexAdapter(getContext(),R.layout.index_item, indexItemList);
        ListView listView=(ListView)view.findViewById(R.id.mListView);
        listView.setAdapter(indexAdapter);
        //initTomorrow(view);
        return view;
    }

    void initIndex(){    //初始化指数
        IndexItem indexItem = new IndexItem("舒适度",MyApplication.todayWeather.getIndex_comfort(),R.drawable.index_comfort);
        indexItemList.add(indexItem);
        indexItem = new IndexItem("穿衣指数",MyApplication.todayWeather.getIndex_cloth(),R.drawable.index_cloth);
        indexItemList.add(indexItem);
        indexItem = new IndexItem("感冒指数",MyApplication.todayWeather.getIndex_influenza(),R.drawable.index_influenza);
        indexItemList.add(indexItem);
        indexItem = new IndexItem("晾晒指数",MyApplication.todayWeather.getIndex_suncure(),R.drawable.index_suncure);
        indexItemList.add(indexItem);
        indexItem = new IndexItem("旅游指数",MyApplication.todayWeather.getIndex_tour(),R.drawable.index_tour);
        indexItemList.add(indexItem);
        indexItem = new IndexItem("紫外线强度",MyApplication.todayWeather.getIndex_ultraviolet(),R.drawable.index_ultraviolet);
        indexItemList.add(indexItem);
        indexItem = new IndexItem("运动指数",MyApplication.todayWeather.getIndex_sport(),R.drawable.index_sport);
        indexItemList.add(indexItem);
        indexItem = new IndexItem("约会指数",MyApplication.todayWeather.getIndex_date(),R.drawable.index_date);
        indexItemList.add(indexItem);
    }

}
