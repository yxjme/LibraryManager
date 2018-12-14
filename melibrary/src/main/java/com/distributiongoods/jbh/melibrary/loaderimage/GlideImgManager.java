package com.distributiongoods.jbh.melibrary.loaderimage;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


/**
 * Created by zbsdata on 2017/5/4.
 */
public class GlideImgManager {



    /**
     * load normal  for img
     *
     * @param url
     * @param iv
     */
    public static void glideLoadingImg(final Context context,
                                       final String url,
                                       final ImageView iv,
                                       final int placeholderImg,
                                       final int errorImg) {
        try{
            Observable.empty()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            Glide.with(context)
                                    .load(url)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                    .override(LogUtil.dp2px(100, MyApplication.getContext()), LogUtil.dp2px(100,MyApplication.getContext()))
                                    .placeholder(placeholderImg)
                                    .error(errorImg)
                                    .into(iv);
                        }
                    }).subscribe();
        }catch (IllegalArgumentException e ) {
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }


    /**
     * load normal  for  circle or round img
     * @param url
     * @param iv
     */
    public static void glideCircleLoader(Context context, String url, ImageView iv, int radius) {
        Glide.with(context)
                .load(url)
//                .placeholder(Utils.getPlaceholder())
//                .error(Utils.getPlaceholder())
                .transform(new GlideCircleTransform(context))
                .into(iv);
    }


    /**
     * load normal  for  circle or round img
     * @param url
     * @param iv
     */
    public static void glideRoundLoader(Context context, String url, ImageView iv,  int radius) {
        Glide.with(context)
                .load(url)
//                .placeholder(Utils.getPlaceholder())
//                .error(Utils.getPlaceholder())
                .transform(new GlideRoundTransform(context,radius))
                .into(iv);
    }


    /**
     *
     */
    public static void glideLoader(final Context context, final String url, final ImageView iv, final int w, final int h) {
        try{
            Observable.empty()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            Glide.with(context)
                                    .load(url)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                    .override(LogUtil.dp2px(w, MyApplication.getContext()), LogUtil.dp2px(h,MyApplication.getContext()))
//                                    .placeholder(Utils.getPlaceholder())
//                                    .error(Utils.getPlaceholder())
                                    .into(iv);
                        }
                    }).subscribe();
        }catch (IllegalArgumentException e ) {
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }
}
