package com.ys.administrator.mydemo.http;

import com.ys.administrator.mydemo.model.BaseBean;
import com.ys.administrator.mydemo.model.ConnectBean;
import com.ys.administrator.mydemo.model.FileUpBean;
import com.ys.administrator.mydemo.model.ProjectInfoBean;
import com.ys.administrator.mydemo.model.ProjectListBean;
import com.ys.administrator.mydemo.model.StatusListBean;
import com.ys.administrator.mydemo.model.UserInfoBean;
import com.ys.administrator.mydemo.model.UserInfoDetialBean;
import com.ys.administrator.mydemo.util.Constant;
import com.ys.administrator.mydemo.util.SPUtil;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
     * 登录
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("/user/login/wx")
    Observable<Response<UserInfoBean>> getWXLogin(@Body RequestBody body);
    /**
     * 绑定/换绑手机号码
     * @return
     */
    @GET("/user/setMobile")
    Observable<Response<UserInfoBean>> setMobile(@HeaderMap Map<String,String> map,@Body RequestBody body);
    /**
     * 获取token
     * @return
     */
    @GET("/user/token")
    Observable<Response<UserInfoBean>> getUserToken(@HeaderMap Map<String,String> map);
    /**
     * 获取项目进度列表
     * @return
     */
    @GET("/project/status/list")
    Observable<Response<StatusListBean>> getStatusList();
    /**
     * 获取项目类型选项列表
     * @return
     */
    @GET("/project/type/list")
    Observable<Response<StatusListBean>> getTypeList();
    /**
     * 获取项目详情
     * @return
     */
    @GET("/project/info")
    Observable<Response<ProjectInfoBean>> getPeojectDetail(@HeaderMap Map<String,String> map, @Query("id") int key);
    /**
     * 获取客服咨询
     * @return
     */
    @GET("/contact")
    Observable<Response<ConnectBean>> getContact();
    /**
     * 获取个人资料
     * @return
     */
    @GET("/user/my")
    Observable<Response<UserInfoDetialBean>> getUserDetialInfo(@HeaderMap Map<String,String> map);
    /**
     * 保存个人资料
     * @return
     */
    @POST("/user/my")
    Observable<Response<UserInfoDetialBean>> saveUserDetialInfo(@HeaderMap Map<String,String> map,@Body RequestBody body);
    /**
     * 上传头像
     * @return
     */
    @Multipart
    @POST("/upload/avatar")
    Observable<Response<FileUpBean>> uploadAavatar(@HeaderMap Map<String,String> map, @Part MultipartBody.Part file);
    /**
     * 搜索项目
     * @return
     */

    @GET("/project/search")
    Observable<Response<ProjectListBean>> getProjectSearch(@HeaderMap Map<String,String> map, @QueryMap Map<String,String> quremap);
    @GET("/project/search")
    Observable<Response<ProjectListBean>> getProjectSearch(@HeaderMap Map<String,String> map);
    /**
     * 新建项目
     * @return
     */

    @POST("/project")
    Observable<Response<BaseBean>> creatProject(@HeaderMap Map<String,String> map,@Body RequestBody body);
    /**
     * 新建项目
     * @return
     */
    @DELETE("/upload/project/data")
    Observable<Response<BaseBean>> deleteFile(@HeaderMap Map<String,String> map,@QueryMap Map<String,String> quremap);
    /**
     * 上传资料
     * @return
     */
    @Multipart
    @POST("/upload/project/data")
    Observable<Response<Map>> uploadFile(@HeaderMap Map<String,String> map,@Query("projectId") int projectId,@Query("dir") String dir, @Part MultipartBody.Part file);

    @GET("/oauth2/access_token")
    Observable<String> getWXAccessToken(@QueryMap Map<String,String> map);

    @GET("/userinfo")
    Observable<String> getUserInfo(@QueryMap Map<String,String> map);
}
