package com.distributiongoods.jbh.melibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by zbsdata on 2018/8/22.
 */



public class SharedPreferencesUtil {


    private static final String  NAME="DATA";

    /**
     *
     * @param context
     * @param key
     * @param values
     */
    public static void putString(Context context,String  key , String values){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,values);
        editor.commit();
    }


    /**
     * @param context
     * @param key
     * @return
     */
    public static String getStringValuse(Context context,String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        String val = sharedPreferences.getString(key,null);
        return val;
    }




    /**
     * @param context
     * @param key
     * @param values
     */
    public static void putInt(Context context,String  key , int values){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,values);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @return
     */
    public static int getIntValues(Context context,String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        int val = sharedPreferences.getInt(key,0);
        return val;
    }


    /**
     * @param context
     * @param key
     * @param values
     */
    public static void putLong(Context context,String  key , long values){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key,values);
        editor.commit();
    }


    /**
     * @param context
     * @param key
     * @return
     */
    public static Long getLongValues(Context context,String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        Long val = sharedPreferences.getLong(key,0);
        return val;
    }



    /**
     * @param context
     * @param key
     * @param values
     */
    public static void putFloat(Context context,String  key , Float values){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key,values);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @return
     */
    public static Float getFloatValues(Context context,String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        Float val = sharedPreferences.getFloat(key,0);
        return val;
    }


    /**
     * @param context
     * @param key
     * @param values
     */
    public static void putBoolean(Context context,String  key , Boolean values){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,values);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @return
     */
    public static Boolean getBooleanValues(Context context,String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        Boolean val = sharedPreferences.getBoolean(key,false);
        return val;
    }



    /**
     * @param context
     * @param key
     * @param values
     */
    public static void putStringSet(Context context,String  key , Set<String> values){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key,values);
        editor.commit();
    }



    /**
     * @param context
     * @param key
     * @return
     */
    public static Set<String> getStringSetValues(Context context,String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        Set<String> val = sharedPreferences.getStringSet(key,null);
        return val;
    }
}

