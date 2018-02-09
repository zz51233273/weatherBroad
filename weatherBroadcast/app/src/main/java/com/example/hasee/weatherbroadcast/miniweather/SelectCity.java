package com.example.hasee.weatherbroadcast.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.baidu.speech.asr.SpeechConstant;
import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.app.MyApplication;
import com.example.hasee.weatherbroadcast.bean.TodayWeather;
import com.example.hasee.weatherbroadcast.database.City;
import com.example.hasee.weatherbroadcast.database.DBHelper;
import com.example.hasee.weatherbroadcast.speechrecognizer.MyEventListener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by hasee on 2018/1/27.
 */

public class SelectCity extends Activity implements AdapterView.OnItemSelectedListener,View.OnClickListener ,View.OnTouchListener  {

    private ImageView mBackBtn;
    private Button speak;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private Spinner spinner1;
    protected static Spinner spinner2;
    private static List<String> provinces;
    private List<String> citys;
    protected static List<String> codes;
    private TextView testview;
    private String cityName;
    private DBHelper dbHelper;    //用于创建帮助器对象（处理数据库相关操作）
    private SQLiteDatabase database;    //用于创建数据库对象
    private boolean flag;
    private ImageView bg;
    private MyEventListener myEventListener;
    private static final int UPDATE_CITY = 1;
    private static final int CANNOT_RECOGNIZE=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
        speak=(Button)findViewById(R.id.speak);
        speak.setOnClickListener(this);
        speak.setOnTouchListener(this);
        testview=(TextView)findViewById(R.id.title_name);
        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        dbHelper = new DBHelper(SelectCity.this, "city.db", null, 3);//创建帮助器对象
        spinner1=(Spinner)findViewById(R.id.select_provinces);
        spinner2=(Spinner)findViewById(R.id.select_citys);
        if(null==provinces){
            provinces=new ArrayList<>();
            addProvinces();
        }
        citys=new ArrayList<>();
        codes=new ArrayList<>();

        //定义适配器
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,provinces);
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,citys);

        //3.adapter设置下拉样式
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //4.spinner加载适配器
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);

        //5.spinner设置监听器
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        spinner1.setTag(1);     //设置标签，用于判断当前选择了哪一个下拉框
        spinner2.setTag(2);
        chooseBackground();
        myEventListener=new MyEventListener(this,SelectCity.this);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case UPDATE_CITY:
                    spinner1.setSelection(msg.arg1);
                    MediaPlayer.create(SelectCity.this, R.raw.bdspeech_recognition_success).start();
                    changeCityByProvince((String) msg.obj);
                    break;
                case CANNOT_RECOGNIZE:
                    Toast.makeText(SelectCity.this,"未搜索到指定城市",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {         //监听按下抬起事件
        if(v.getId() == R.id.speak){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {  //按下
                MediaPlayer.create(this,R.raw.bdspeech_recognition_start).start();
                myEventListener.getStartFunction();
            }
            else if(event.getAction()==MotionEvent.ACTION_UP){  //抬起
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cursor cursor=null;
                        database=dbHelper.getWritableDatabase();        //通过数据库查找结果
                        String city="";
                        while((null==cursor||!cursor.moveToFirst())&&myEventListener.isListening){  //仍为搜索到并且正在监听
                            if(null!=cursor)cursor.close();
                            city=myEventListener.getCitySpeaked();
                            if(""!=city){
                                cursor=dbHelper.QueryProvinceByCity(database,city);
                            }
                        }
                        if(null!=cursor&&cursor.moveToFirst()){
                            flag=false;
                            int pos=adapter1.getPosition(cursor.getString(cursor.getColumnIndex(City.KEY_PROVINCE)));   //得到一个省在下拉框中的位置
                            Message msg =new Message();
                            msg.what = UPDATE_CITY;
                            msg.obj = city ;
                            msg.arg1 = pos;
                            mHandler.sendMessage(msg);
                        }else{
                            Message msg =new Message();
                            msg.what = CANNOT_RECOGNIZE;
                            mHandler.sendMessage(msg);
                        }
                        if(null!=cursor)cursor.close();
                        myEventListener.getStopFunction();              //停止语音识别
                    }
                }).start();
            }
        }
        return false;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int tag=(Integer) parent.getTag();
       // Log.d("test",tag+"");
        switch (tag){
            case 1:
                chooseProvine();
                break;
            case 2:
                testview.setText(adapter2.getItem(position));
                break;
        }

    }
    void chooseBackground(){        //随机选择背景图片
        bg=(ImageView)findViewById(R.id.bg);
        int m= new Random().nextInt(6);
        switch (m){
            case 0: bg.setImageResource(R.drawable.cbg0);
                break;
            case 1: bg.setImageResource(R.drawable.cbg1);
                break;
            case 2: bg.setImageResource(R.drawable.cbg2);
                break;
            case 3: bg.setImageResource(R.drawable.cbg3);
                break;
            case 4: bg.setImageResource(R.drawable.cbg4);
                break;
            case 5: bg.setImageResource(R.drawable.cbg5);
                break;
        }

    }
    public void chooseProvine(){
        database = dbHelper.getWritableDatabase();  //创建数据库对象
        Cursor cursor=null;
        /*
            实现功能：选择城市时，下拉框默认值为主页面所选的省份和城市
         */
        String defCity="";
        if(!flag&&!"".equals(getIntent().getStringExtra("keycode"))){          //设置省份默认值，此页面中只执行一次
            cursor=dbHelper.QueryByCode(database,getIntent().getStringExtra("keycode"));
            if (cursor.moveToFirst()) {
                int pos=adapter1.getPosition(cursor.getString(cursor.getColumnIndex(City.KEY_PROVINCE)));   //得到一个省在下拉框中的位置
                defCity=cursor.getString(cursor.getColumnIndex(City.KEY_CITY));
                spinner1.setSelection(pos);
            }
        }
        changeCityByProvince(defCity);
    }

    void changeCityByProvince(String city){    //根据选择的省份，实时变化城市选项
        Cursor cursor=null;
        cityName = (String) spinner1.getSelectedItem();
        testview.setText(cityName);
        database=dbHelper.getWritableDatabase();
        cursor=dbHelper.QueryByProvince(database,cityName);  //读取城市信息
        citys.clear();      //清空原有城市数据
        codes.clear();      //清空原有相应的城市代码
        addCitysAndCodes(cursor);
        adapter2.notifyDataSetChanged();                       //通知spinner刷新数据 ***** 很必要
        if(!flag&&!"".equals(getIntent().getStringExtra("keycode"))){       //设置城市默认值，此页面中只执行一次
            adapter2.notifyDataSetChanged();                   //通知spinner刷新数据 ***** 很必要
            int pos=adapter2.getPosition(city);      //得到city这个城市在下拉框中的位置
            spinner2.setSelection(pos);               //设置spinner2下拉框默认值
            flag=true;
        }
        database.close();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void addProvinces(){                 //往List provinces中添加省份
        database=dbHelper.getWritableDatabase();
        Cursor cursor=dbHelper.onQueryProvince(database);
        if (cursor.moveToFirst()) {
            do {
                City c = new City();
                c.setProvince(cursor.getString(cursor.getColumnIndex(c.KEY_PROVINCE)));
                if(provinces.indexOf(c.getProvince())==-1){      //保证省份不重复出现
                    provinces.add(c.getProvince());
                }
            }while (cursor.moveToNext());
        }

    }
    void addCitysAndCodes(Cursor cursor){       //往List citys，codes中添加城市和相应城市代码
        if (cursor.moveToFirst()) {
            do {
                City c = new City();
                c.setCityName(cursor.getString(cursor.getColumnIndex(c.KEY_CITY)));
                c.setCode(cursor.getInt(cursor.getColumnIndex(c.KEY_CODE)));
                citys.add(c.getCityName());
                codes.add(String.valueOf(c.getCode()));
            } while (cursor.moveToNext());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myEventListener.getAsr().send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        if (myEventListener.getEnableOffline()) {
            myEventListener.getUnloadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }
}
