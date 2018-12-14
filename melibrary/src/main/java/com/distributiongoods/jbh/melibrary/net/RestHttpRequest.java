package com.distributiongoods.jbh.melibrary.net;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by zbsdata on 2017/9/8.
 */

public class RestHttpRequest {

    /**
     * 首先实例化okhttp
     * @return
     */
    private static OkHttpClient.Builder okHttp=new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.MINUTES);

    /**
     * 获取的Retrofit的对象
     */
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(Api.HOST);



    /**
     * @param serviceClass
     * @param <S>
     * @return
     */
    public synchronized static <S> S createService(Class<S> serviceClass) {
        okHttp.interceptors().add(new HttpInterceptor());
        Retrofit retrofit = builder.client(okHttp.build()).build();
        return retrofit.create(serviceClass);
    }


    /**
     *
     */
    private static class HttpInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            Request request = builder.addHeader("Content-type", "application/json").build();
            Response response = chain.proceed(request);
            return response;
        }
    }


    /**
     *
     */
    private static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody,Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }


//
//    /**
//     * 获取服务器的时间
//     *
//     * @param callBack
//     */
//    public static void getServerTime(final CallBack<ServerTimeResponse> callBack){
//        ApiService apiService=RestHttpRequest.createService(ApiService.class);
//        BaseBean baseBean=new BaseBean("","") {
//            @Override
//            public Map map() {
//                return setSign(md5Sign().toString()).requestBody();
//            }
//        };
//
//        apiService.request(PostJson.getPostContentRequestBody(baseBean.map()))
//                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ServerTimeResponse>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        callBack.error(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(ServerTimeResponse o) {
//                        callBack.success(o);
//                    }
//                });
//
//    }



    /**
     * @param url
     * @param fileName
     */
    public static void downLoad(String url, final String fileName){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                   /*获取下载文件输入流*/
                    InputStream is = response.body().byteStream();
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                   /* 检查本地文件是否存在*/
                    String path="";
//                    String path = Utils.getSaveDir(MyApplication.getContext()) + File.separator + "images";
                    /* 实例化文件夹对象  用于存放主题的文件夹*/
                    File pathFile = new File(path);
                    if (!pathFile.exists()) {
                    /* 如果文件不存在   创建文件*/
                        pathFile.mkdirs();
                    }
                    pathFile.listFiles();
                    String photoPath = path +File.separator+ fileName;
                   /*创建文件对象，用来存储新的图像文件*/
                    File file = new File(photoPath);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream outputStream = new FileOutputStream(file);
                    BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
