package com.airsaid.pickerviewlibrary;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.airsaid.pickerviewlibrary.bean.Area;
import com.airsaid.pickerviewlibrary.bean.City;
import com.airsaid.pickerviewlibrary.bean.Province;
import com.airsaid.pickerviewlibrary.bean.Result;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zbsdata on 2017/2/27.
 */

public class PickerCityUtils extends OptionsPickerView {

    private Context mContext;

    // 省数据集合
    private ArrayList<String> mListProvince = new ArrayList<String>();

    // 市数据集合
    private ArrayList<ArrayList<String>> mListCity = new ArrayList<ArrayList<String>>();

    // 区数据集合
    private ArrayList<ArrayList<ArrayList<String>>> mListArea = new ArrayList<ArrayList<ArrayList<String>>>();

    public PickerCityUtils(Context context) {
        super(context);
        this.mContext = context;
        initJsonData();
        initCitySelect();
    }


    private void initCitySelect() {
        setTitle("选择城市");
        setPicker(mListProvince, mListCity, mListArea, true);
        setCyclic(false, false, false);
        setSelectOptions(0, 0, 0);
        setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                if(mOnCitySelectListener != null){
                    if(mListCity.size() > option1 && mListCity.get(option1).size() > option2){
                        if(mListArea.size() > option1 && mListArea.get(option1).size() > option2
                                && mListArea.get(option1).get(option2).size() > option3){
                            String str = mListProvince.get(option1).concat(mListCity.get(option1).get(option2)).concat(mListArea.get(option1).get(option2).get(option3));
                            mOnCitySelectListener.onCitySelect(str, option1,  option2,  option3);
                            mOnCitySelectListener.onCitySelect(mListProvince.get(option1),mListCity.get(option1).get(option2),mListArea.get(option1).get(option2).get(option3));


                        }
                    }
                }
            }
        });
    }


    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        AssetManager assets = mContext.getAssets();

        try {
            InputStream is = assets.open("json.txt");
            byte[] buf = new byte[is.available()];
            is.read(buf);
            String json = new String(buf, "UTF-8");
            /**/
            Result r=new Gson().fromJson(json,Result.class);

            Log.v("=====mListProvince=",String.valueOf(json));
            List<Province> list= r.getResult();

            for (Province p:list){
                /*省*/
                mListProvince.add(p.getName());
                Log.v("=====省=",p.getName());

                /*市*/
                List<City> city = p.getSubdb();
                mListCity.add(getStringLsit(city));

                /*区*/
                ArrayList<ArrayList<String>> A = new ArrayList<ArrayList<String>>();
                for (City c:city){
                    Log.v("=========省市=",c.getName());
                    List<Area> mA = c.getSubdb();
                    A.add(getStringLsit1(mA));
                    for (Area s:mA){
                        Log.v("=========省市区=",s.getName());
                    }
                }
                mListArea.add(A);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * @param city
     * @return
     */
    public ArrayList<String>  getStringLsit( List<City> city){
        ArrayList<String> cList=new ArrayList<String>();
        for (City c:city){
            cList.add(c.getName());
        }
        return cList;
    }


    /**
     * @param area
     * @return
     */
    public ArrayList<String>  getStringLsit1( List<Area> area){
        ArrayList<String> cList=new ArrayList<String>();
        for (Area c:area){
            cList.add(c.getName());
        }
        return cList;
    }


    public OnCitySelectListener mOnCitySelectListener;

    public interface OnCitySelectListener {
        void onCitySelect(String str,int option1, int option2, int option3);
        void onCitySelect(String p, String city, String area);
    }


    public void setOnCitySelectListener(OnCitySelectListener listener) {
        this.mOnCitySelectListener = listener;
    }
}
