package com.woyou.androidsample.netApi;

import com.woyou.androidsample.bean.FamousInfo;
import com.woyou.androidsample.bean.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Xiho on 2016/3/14.
 */
public interface FamousService {
    @GET("/avatardata/mingrenmingyan/lookup")
    Call<FamousInfo> getFamousResult(@Header("apiKey") String apiKey,
                                     @Query("keyword") String keyword,
                                     @Query("page") int page,
                                     @Query("rows") int rows);

    @GET("/v2/movie/top250")
    Observable<Result> getNewsResult(@Query("start") int start,
                                     @Query("count") int count);

}
