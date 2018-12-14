package com.distributiongoods.jbh.melibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zbsdata on 2018/8/23.
 */

public class AppInfo {

    /**App版本号  100*/
    public static final String VERSION_CODE="versionCode";
    /**版本名字  1.0.0*/
    public static final String VERSION_NAME="versionName";
    /**所有权限数组 PermissionInfo[] */
    public static final String PERMISSIONS="permissions";
    /**app的包名  */
    public static final String PACKAGE_NAME="packageName";
    /**applicationInfo*/
    public static final String APPLICATION_INFO="applicationInfo";

    /**
     * app 信息
     *
     * @param context
     * @return
     */
    public static <T> T getInfo(Context context,String key){
        Map<String,Object>  map=new HashMap<>();
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo i = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            map.put(VERSION_CODE,i.versionCode);
            map.put(VERSION_NAME,i.versionName);
            map.put(PERMISSIONS,i.permissions);
            map.put(PACKAGE_NAME,i.packageName);
            map.put(APPLICATION_INFO,i.applicationInfo);
            T t= (T) map.get(key);
            return t;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
