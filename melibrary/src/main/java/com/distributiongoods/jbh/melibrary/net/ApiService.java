package com.distributiongoods.jbh.melibrary.net;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {

    /**发送短信*/
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(Api.method.SendSms)
    Observable<Object> requestSendMsg(@Body RequestBody requestBody);

}