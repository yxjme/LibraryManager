package com.distributiongoods.jbh.melibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.graphics.Palette;
import android.util.Log;

/**
 * Created by zbsdata on 2018/8/23.
 */

public class NetWorkUtil {


    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnection(Context context){
        ConnectivityManager  manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission")
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (manager!=null && info!=null){
            if (info.isConnected() && NetworkInfo.State.CONNECTED == info.getState()){
                connectedType(info.getType());
                return true;
            }
        }
        return false;
    }




    /**
     * 连接网络类型
     *
     * @param type
     */
    private static void connectedType(int type) {
        switch (type){
            /*连接的网咯是WiFi*/
            case ConnectivityManager.TYPE_WIFI:
                Log.v("==========","连接的网咯是WiFi");
                break;

              /*连接的网咯是手机网路*/
            case ConnectivityManager.TYPE_MOBILE:
                Log.v("==========","连接的网咯是手机网路");
                break;
        }
    }


    /**
     * @param context
     *
     * 检测WiFi网咯信号的强弱
     *
     * @return
     */
    public static NetState isNetStrong(Context context){
        NetState state = NetState.NO_SIGNAL;
        ConnectivityManager  manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo info = manager.getActiveNetworkInfo();
        if (manager!=null && info!=null){
            if (info.isConnected() && NetworkInfo.State.CONNECTED == info.getState()){
                if (info.getType()==ConnectivityManager.TYPE_WIFI){
                    WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    //获得信号强度值
                    int level = wifiInfo.getRssi();
                    //根据获得的信号强度发送信息
                    if (level <= 0 && level >= -50) {
                        //信号最好
                        state=NetState.BEST;
                    } else if (level < -50 && level >= -70) {
                        //信号较好
                        state=NetState.GOOD;
                    } else if (level < -70 && level >= -80) {
                        //信号一般
                        state=NetState.GENERAL;
                    } else if (level < -80 && level >= -100) {
                        //信号较差
                        state=NetState.DIFFERENCE;
                    } else {
                        //无信号
                        state=NetState.NO_SIGNAL;
                    }
                }
            }
        }
        return state;
    }


    /**/
    public enum NetState{
        BEST, GOOD,GENERAL,DIFFERENCE,NO_SIGNAL
    }

}
