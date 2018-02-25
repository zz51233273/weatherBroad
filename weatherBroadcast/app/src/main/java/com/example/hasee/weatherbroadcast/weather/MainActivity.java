package com.example.hasee.weatherbroadcast.weather;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import com.example.hasee.weatherbroadcast.R;
import com.example.hasee.weatherbroadcast.app.MyApplication;
import com.example.hasee.weatherbroadcast.bean.ForecastWeather;
import com.example.hasee.weatherbroadcast.bean.TodayWeather;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.content.ClipboardManager;

import com.example.hasee.weatherbroadcast.database.City;
import com.example.hasee.weatherbroadcast.database.DBHelper;
import com.example.hasee.weatherbroadcast.database.DBManager;
import com.example.hasee.weatherbroadcast.util.NetUtil;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int UPDATE_TODAY_WEATHER = 1;
    private BDLocationListener BaiDuListener = new MyLocation(this);
    private LocationClient mLocationClient = null;
    private LocationClientOption option = new LocationClientOption();
    private FragmentPager fragmentPager;
    private ViewPager vpager;
    private ImageView weatherImg;
    private TextView titleCityName;
    private String code="";
    private boolean isStart=false;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.isAdding){
            updateMyCityWeatherData();
            MyApplication.isAdding=false;
        }
        else if(MyApplication.isSelecting){
            updateWeatherData();
            MyApplication.isSelecting=false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info_port);
        vpager = (ViewPager) findViewById(R.id.vpager);
        init();
        new DBManager(getApplicationContext()).writeData();
    }
    void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                initNavigationDrawer();
            }
        }).start();
        //判断网络状态
        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
            Log.d("myWeather", "网络OK");
            //Toast.makeText(MainActivity.this, "网络OK！", Toast.LENGTH_LONG).show();
        } else {
            Log.d("myWeather", "网络挂了");
            //Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
        }
        initView();
        JudgePermission();
    }

    void initView(){        //初始化对象
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        mLocationClient.registerLocationListener(BaiDuListener);    //注册监听函数
        titleCityName=(TextView)findViewById(R.id.title_city_name);
        weatherImg = (ImageView) findViewById(R.id.weather_img);
        weatherImg.setImageResource(R.drawable.na);
    }

    private void queryWeatherCode(String cityCode) {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;
        Log.d("myWeather", address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con = null;
                try {
                    URL url = new URL(address);
                    con = (HttpURLConnection) url.openConnection();    //建立连接
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {     //读取网页上的内容
                        response.append(str);
                        Log.d("myWeather", str);
                    }
                    String responseStr = response.toString();       //保存内容
                    Log.d("myWeather", responseStr);

                    parseXML(responseStr);           //解析内容
                    if (null!=MyApplication.todayWeather) {
                        Log.d("myWeather", MyApplication.todayWeather.toString());
                        Message msg =new Message();
                        msg.what = UPDATE_TODAY_WEATHER;
                        msg.obj=MyApplication.todayWeather;
                        mHandler.sendMessage(msg);
                    }
                    Log.d("myWeather", MyApplication.forecastWeather.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
            }
        }).start();
    }

    private void parseXML(String xmldata){  //解析XML
        int fengxiangCount=0;
        int fengliCount =0;
        int dateCount=0;
        int highCount =0;
        int lowCount=0;
        int typeCount =0;
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = fac.newPullParser();
            xmlPullParser.setInput(new StringReader(xmldata));
            int eventType = xmlPullParser.getEventType();
            boolean isForecast=false;
            int weatherStep=0;      //初始值代表当前天气信息
            int zhiShu=0;           //代表某一个指数
            Log.d("myWeather", "parseXML");
            while (eventType != XmlPullParser.END_DOCUMENT) {       //读取xml相应内容
                switch (eventType) {
                    // 判断当前事件是否为文档开始事件
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    // 判断当前事件是否为标签元素开始事件
                    case XmlPullParser.START_TAG:
                        if(xmlPullParser.getName().equals("resp")){
                            MyApplication.todayWeather= new TodayWeather();
                            MyApplication.forecastWeather=new ForecastWeather[4];
                            int count=MyApplication.forecastWeather.length;
                            for(int i=0;i<count;i++){
                                MyApplication.forecastWeather[i]=new ForecastWeather();
                            }
                        }
                        if (!isForecast && MyApplication.todayWeather != null) {
                            if (xmlPullParser.getName().equals("city")) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setCity(xmlPullParser.getText());
                                int count=MyApplication.forecastWeather.length;
                                for(int i=0;i<count;i++){
                                    MyApplication.forecastWeather[i].setCity(xmlPullParser.getText());
                                }
                            } else if (xmlPullParser.getName().equals("updatetime")) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setUpdatetime(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("shidu")) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setShidu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setWendu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("pm25")) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setPm25(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("quality")) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setQuality(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount == 0) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setFengxiang(xmlPullParser.getText());
                                fengxiangCount++;
                            } else if (xmlPullParser.getName().equals("fengli") && fengliCount == 0) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setFengli(xmlPullParser.getText());
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 0) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setDate(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 0) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setHigh(xmlPullParser.getText().substring(2).trim());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 0) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setLow(xmlPullParser.getText().substring(2).trim());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 0) {
                                eventType = xmlPullParser.next();
                                MyApplication.todayWeather.setType(xmlPullParser.getText());
                                typeCount++;
                            }else if(xmlPullParser.getName().equals("name")){
                                zhiShu++;
                            }else if(xmlPullParser.getName().equals("detail")){      //穿衣指数
                                switch (zhiShu){
                                    case 2:     //舒适度
                                        eventType = xmlPullParser.next();
                                        MyApplication.todayWeather.setIndex_comfort(xmlPullParser.getText());
                                        break;
                                    case 3:     //穿衣指数
                                        eventType = xmlPullParser.next();
                                        MyApplication.todayWeather.setIndex_cloth(xmlPullParser.getText());
                                        break;
                                    case 4:     //感冒指数
                                        eventType = xmlPullParser.next();
                                        MyApplication.todayWeather.setIndex_influenza(xmlPullParser.getText());
                                        break;
                                    case 5:     //晾晒指数
                                        eventType = xmlPullParser.next();
                                        MyApplication.todayWeather.setIndex_suncure(xmlPullParser.getText());
                                        break;
                                    case 6:     //旅游指数
                                        eventType = xmlPullParser.next();
                                        MyApplication.todayWeather.setIndex_tour(xmlPullParser.getText());
                                        break;
                                    case 7:     //紫外线强度
                                        eventType = xmlPullParser.next();
                                        MyApplication.todayWeather.setIndex_ultraviolet(xmlPullParser.getText());
                                        break;
                                    case 9:     //运动指数
                                        eventType = xmlPullParser.next();
                                        MyApplication.todayWeather.setIndex_sport(xmlPullParser.getText());
                                        break;
                                    case 10:    //约会指数
                                        eventType = xmlPullParser.next();
                                        MyApplication.todayWeather.setIndex_date(xmlPullParser.getText());
                                        isForecast=true;
                                        break;
                                }
                            }
                        } else if(weatherStep>=1){          //读取未来几天的天气信息
                            if (xmlPullParser.getName().equals("type") &&typeCount==0) {
                                eventType = xmlPullParser.next();
                                MyApplication.forecastWeather[weatherStep-1].setType(xmlPullParser.getText());
                                typeCount++;
                            } else if (xmlPullParser.getName().equals("high")) {
                                eventType = xmlPullParser.next();
                                MyApplication.forecastWeather[weatherStep-1].setHigh(xmlPullParser.getText().substring(2).trim());
                            } else if (xmlPullParser.getName().equals("low") ) {
                                eventType = xmlPullParser.next();
                                MyApplication.forecastWeather[weatherStep-1].setLow(xmlPullParser.getText().substring(2).trim());
                            } else if (xmlPullParser.getName().equals("date") ) {
                                eventType = xmlPullParser.next();
                                MyApplication.forecastWeather[weatherStep-1].setDate(xmlPullParser.getText());
                            }
                        }
                        break;

                    // 判断当前事件是否为标签元素结束事件
                    case XmlPullParser.END_TAG:
                        if(xmlPullParser.getName().equals("weather")){      //根据xml内容可知，一个weather标签内是一天的天气信息
                            isForecast=true;
                            weatherStep++;
                            typeCount=0;
                        }else if(xmlPullParser.getName().equals("forecast")){
                            isForecast=false;
                        }
                        break;
                }
                // 进入下一个元素并触发相应事件
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void updateTodayWeather(TodayWeather todayWeather){         //更新当前天气信息,在Handler中被调用
        chooseWeatherImg(todayWeather);
        fragmentPager=new FragmentPager(getSupportFragmentManager(),vpager);
    }

    void chooseWeatherImg(TodayWeather weather){            //根据当前天气信息，更改天气图片
        titleCityName.setText(weather.getCity()+"天气");
        if(null!=weather){
            String updatetime;      //当前时间
            updatetime=MyApplication.todayWeather.getUpdatetime();
            updatetime=updatetime.substring(0,updatetime.indexOf(":"));
            changeImg(updatetime,MyApplication.todayWeather.getType());
        }
    }
    public void changeImg(String updatetime,String type){
        int nowTime=Integer.parseInt(updatetime);
        if(nowTime>=6&&nowTime<19){         //代表早上时间
            if(nowTime==18) {
                findViewById(R.id.toolbar).setBackgroundResource(R.drawable.title_night);
            }
            else {
                findViewById(R.id.toolbar).setBackgroundResource(R.drawable.title_sun);
            }
        }else{
            findViewById(R.id.toolbar).setBackgroundResource(R.drawable.title_night);
        }
        MyApplication.todayWeather.setWeatherImg(weatherImg);
    }

    void updateWeatherData(){
        if(null!=SelectCity.spinner2){
            int pos=SelectCity.spinner2.getSelectedItemPosition();
            code=SelectCity.codes.get(pos);
        } else if("".equals(code)){
            code="101010100";
        }
        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
            Log.d("myWeather", "网络OK");
            queryWeatherCode(code);
        } else {
            Log.d("myWeather", "网络挂了");
            Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
        }
    }

    void updateMyCityWeatherData(){
        if(!"".equals(MyCitys.myCode)){
            code=MyCitys.myCode;
            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络OK");
                queryWeatherCode(code);
            } else {
                Log.d("myWeather", "网络挂了");
                Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
            }
        }else{
            updateWeatherData();
        }
    }

    protected void onRefresh(){
        SelectCity.spinner2=null;
        finish();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void initLocation() {
        option.setCoorType("bd09ll");        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);        //可选，默认false,设置是否使用gps
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mLocationClient.setLocOption(option);
    }

    private void  requestLocation(){
        initLocation();
        Log.d("map requestLocation:","Run requestLocation ...");
        mLocationClient.start();
    }

    @Override
    public  void  onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults){     //检查权限
        switch (requestCode){
            case 1:
                if(grantResults.length >0 ){
                    for (int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"all  permission  !!!",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"Unkonw error.",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    private void JudgePermission(){     //检查权限
        List<String> permissionList = new ArrayList<>();// Permission array list , request permissions in one array.
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);//request all permissions
        }else {
            requestLocation();// Request Location information
        }
    }

    protected static void addCity(String city){

    }
    private void copyWeatherMessage(){              //把当前天气信息复制到黏贴板上
        if(null!= MyApplication.todayWeather){
            StringBuffer mes=new StringBuffer();
            mes.append(MyApplication.todayWeather.getCity()+"\n");

            if(!"".equals(MyApplication.todayWeather.getPm25())&&null!=MyApplication.todayWeather.getPm25())
                mes.append("pm2.5指数："+MyApplication.todayWeather.getPm25()+"\n");

            mes.append("气温："+MyApplication.todayWeather.getLow()+" ~ "+MyApplication.todayWeather.getHigh()+"\n");
            mes.append(MyApplication.todayWeather.getType()+"\n");

            if(!"".equals(MyApplication.todayWeather.getIndex_cloth())&&null!=MyApplication.todayWeather.getIndex_cloth())
                mes.append("穿衣指数:"+"\n"+MyApplication.todayWeather.getIndex_cloth()+"\n");

            if(!"".equals(MyApplication.todayWeather.getIndex_date())&&null!=MyApplication.todayWeather.getIndex_date())
                mes.append("约会指数:"+"\n"+MyApplication.todayWeather.getIndex_date()+"\n");

            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", mes);
            cm.setPrimaryClip(mClipData);
            Toast.makeText(this,"已复制天气信息至粘贴板上",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"当前无天气信息",Toast.LENGTH_LONG).show();
        }
    }

    private void initNavigationDrawer(){        //初始化左菜单栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        StringBuffer message=new StringBuffer();
        message.append("此app为摩洱天气预报\n")
                .append("制作人 : zz51233273\n")
                .append("图片来源 : 互联网 & 自创\n");
        builder.setMessage(message);
        builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    /*
    * 菜单栏 + DrawerLayout+NavigationView布局
    * */
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {     //标题栏图标监听
                case R.id.base_action_city:
                    onRefresh();
                    break;
                case R.id.action_share:
                    copyWeatherMessage();
                    break;
                case R.id.action_update:
                    updateWeatherData();
                    break;
            }
            return true;
        }
    };

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (id){
            case R.id.nav_city:
                Intent i = new Intent(this, SelectCity.class);
                i.putExtra("keycode",code);
                startActivity(i);
                break;
            case R.id.nav_about:
                getDialog();
                break;
            case R.id.nav_add:
                Intent i2 = new Intent(this, MyCitys.class);
                i2.putExtra("keycode",code);
                startActivity(i2);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    class MyLocation implements BDLocationListener {        //定位监听类
        private DBHelper dbHelper;            //用于创建帮助器对象（处理数据库相关操作）
        private SQLiteDatabase database;     //用于创建数据库对象
        private Context context;
        public MyLocation(Context context){
            this.context=context;
        }
        @Override
        public  void onReceiveLocation(BDLocation location) {
            final StringBuilder currentPosition = new StringBuilder();
            if (location.getLocType() == BDLocation.TypeGpsLocation) {   // GPS定位结果
                currentPosition.append("\nGPS定位成功");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {  // Net定位结果
                currentPosition.append(location.getAddrStr());    //获取地址信息
                currentPosition.append("\n[运营商]");
                currentPosition.append(location.getOperators());    //获取运营商信息
                currentPosition.append("\n网络定位成功！");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {                // 离线定位结果
                currentPosition.append("离线定位成功，离线定位结果也是有效的\n");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                currentPosition.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因\n");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                currentPosition.append("网络不同导致定位失败，请检查网络是否通畅\n");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                currentPosition.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机\n");
            } else {
                currentPosition.append("定位失败。错误码：\n");
            }

            if (null != location) {
                dbHelper = new DBHelper(context, "city.db", null, 3);
                database = dbHelper.getWritableDatabase();
                code = SelectCityCode(location.getProvince(), location.getCity());      //得到定位到的城市的城市代码
                database.close();
                updateWeatherData();
            }
        }
        public String SelectCityCode(String province,String city){   //得到相应城市代码
            province=province.substring(0,province.length()-1);
            city=city.substring(0,city.length()-1);
            Cursor cursor=dbHelper.QueryCodeByProAndCity(database,province,city);
            String code="";
            if(cursor.moveToFirst()){
                code=cursor.getString(cursor.getColumnIndex(City.KEY_CODE));
            }
            return code;
        }
    }
}

