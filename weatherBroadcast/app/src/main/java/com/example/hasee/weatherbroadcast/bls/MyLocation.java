package com.example.hasee.weatherbroadcast.bls;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.example.hasee.weatherbroadcast.database.City;
import com.example.hasee.weatherbroadcast.database.DBHelper;
import com.example.hasee.weatherbroadcast.miniweather.MainActivity;

/**
 * Created by hasee on 2018/2/3.
 */

public class MyLocation implements BDLocationListener {
    private DBHelper dbHelper;            //用于创建帮助器对象（处理数据库相关操作）
    private SQLiteDatabase database;     //用于创建数据库对象
    private Context context;
    public static String cityCode="";
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
            cityCode = SelectCityCode(location.getProvince(), location.getCity());      //得到定位到的城市的城市代码
            database.close();
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
