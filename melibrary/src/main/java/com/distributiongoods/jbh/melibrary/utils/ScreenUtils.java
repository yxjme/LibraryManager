package com.distributiongoods.jbh.melibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by zbsdata on 2018/8/23.
 */

public class ScreenUtils {


    /**
     * 第一种获取屏幕的宽高
     *
     * @param context
     * @return
     */
    public static int[] screenWidthAndHeight1(Context context){
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return new int[]{display.getWidth(),display.getHeight()};
    }


    /**
     * 第二种获取屏幕的宽高
     *
     * @param context
     * @return
     */
    public static int[] screenWidthAndHeight2(Activity context){
        WindowManager manager= context.getWindowManager();
        Display display = manager.getDefaultDisplay();
        return new int[]{display.getWidth(),display.getHeight()};
    }


    /**
     * 第三种获取屏幕的宽高
     *
     * @param context
     * @return
     */
    public static int[] screenWidthAndHeight3(Activity context){
        WindowManager manager= context.getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        return new int[]{displayMetrics.widthPixels,displayMetrics.heightPixels};
    }

    /**
     * 第四种获取屏幕宽高
     *
     * @param context
     * @return
     */
    public static int[] screenWidthAndHeight4(Context context){
        Resources res = context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels,displayMetrics.heightPixels};
    }



    /**
     * dp 转换成 px
     *
     * @param context
     * @param val
     * @return
     */
    public static float  dipToPx(Context context,int val){
        float density = context.getResources().getDisplayMetrics().density;
        return density*val+0.5f;
    }


    /**
     * px 转换成 dip
     *
     * @param context
     * @param val
     * @return
     */
    public static float  pxToDip(Context context,int val){
        float density = context.getResources().getDisplayMetrics().density;
        return val/density+0.5f;
    }



    /** px转换sp */
    public static int px2sp(Context context,int pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }




    /** sp转换px */
    public static int sp2px(Context context,int spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }





    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int statusBarHegiht(Context context){
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }





}
