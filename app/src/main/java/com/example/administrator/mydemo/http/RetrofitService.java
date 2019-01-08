package com.example.administrator.mydemo.http;

import com.example.administrator.mydemo.model.CaipiaoBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitService {
    @GET("types")
    Call<CaipiaoBean> getFarm(@Query("key") String key);

    @POST("/blog")
    Observable<Result<String>> getBlogs();
    @GET("types")
    Observable<Response<CaipiaoBean>> getCaipiao(@Query("key") String key);

    @GET("/oauth2/access_token")
    Observable<String> getWXAccessToken(@QueryMap Map<String,String> map);

    @GET("/userinfo")
    Observable<String> getUserInfo(@QueryMap Map<String,String> map);
}
