package com.ys.administrator.mydemo.http;

import com.ys.administrator.mydemo.model.BaseBean;
import com.ys.administrator.mydemo.model.StatusListBean;
import com.ys.administrator.mydemo.model.UserInfoBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitService {
    @GET("types")
    Call<BaseBean> getFarm(@Query("key") String key);

    /**
     * 注册
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("/user/signup")
    Observable<Response<UserInfoBean>> getSingUp(@Body RequestBody body);
    /**
     * 找回密码
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("/user/setPasswd")
    Observable<Response<UserInfoBean>> getSetPasswd(@Body RequestBody body);
    /**
     * 发验证短信
     * @param key
     * @return
     */
    @GET("/user/smsCode")
    Observable<Response<BaseBean>> getSmsCode(@Query("mobile") String key);
    /**
     * 登录
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("/user/login")
    Observable<Response<UserInfoBean>> getLogin(@Body RequestBody body);
    /**
     * 获取项目进度列表
     * @return
     */
    @GET("/project/status/list")
    Observable<Response<StatusListBean>> getStatusList();

    @GET("/oauth2/access_token")
    Observable<String> getWXAccessToken(@QueryMap Map<String,String> map);

    @GET("/userinfo")
    Observable<String> getUserInfo(@QueryMap Map<String,String> map);
}
