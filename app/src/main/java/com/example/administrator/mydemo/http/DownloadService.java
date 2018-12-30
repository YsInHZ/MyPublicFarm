package com.example.administrator.mydemo.http;




import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DownloadService {

    @Streaming
    @GET
    Observable<Response<ResponseBody>> retrofitDownloadFile(@Url String url, @Header("If-Range") String lastModify, @Header("Range") String range);

}
