package com.distributiongoods.jbh.melibrary.net;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 *
 */
public class PostJson {

    /**
     * @param map
     * @return
     */
    public static RequestBody getPostContentRequestBody(Map<String, Object> map){
        Map<String , Object> jsonContent=new HashMap<>();
        if(map==null || map.size()==0){
            throw new NullPointerException("map can not null");
        }
        jsonContent.put("data",map);
        String content=new Gson().toJson(jsonContent);
        Log.v("post=","content:"+content);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),content);
    }
}
