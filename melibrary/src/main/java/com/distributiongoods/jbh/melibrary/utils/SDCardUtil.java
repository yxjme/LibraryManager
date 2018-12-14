package com.distributiongoods.jbh.melibrary.utils;

import android.os.Environment;
import android.os.StatFs;

/**
 * Created by zbsdata on 2018/8/22.
 */

public class SDCardUtil {


    /**
     * 判断是够存在sdcard
     * @return
     */
    public static boolean existSDCard(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED ) ? true : false;
    }




    /**
     * Sdcard的根目录的path
     *
     * @return
     */
    public static String sdCardRootPath(){
        if(existSDCard()){
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }



    /**
     * sdcard的可用空间大小
     *
     *  单位MB
     *
     * @return
     */
    public static long sdCardSize(){
        if(existSDCard()){
            StatFs statFs=new StatFs(Environment.getExternalStorageDirectory().getPath());
            /*单个数据块*/
            long blockSize=statFs.getBlockSize();
             /*空闲数据块*/
            long freeBlocks=statFs.getFreeBlocks();
            return ( freeBlocks * blockSize ) / 1024 / 1024;
        }
        return 0;
    }
}
