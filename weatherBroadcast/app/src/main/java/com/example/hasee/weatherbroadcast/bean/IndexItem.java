package com.example.hasee.weatherbroadcast.bean;

/**
 * Created by hasee on 2018/2/9.
 */

public class IndexItem {
    private String name;
    private String value;
    private int imgId;

    public IndexItem(String name, String value, int imgId){
        this.name=name;
        this.value=value;
        this.imgId=imgId;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

    public int getImgId(){
        return imgId;
    }

}
