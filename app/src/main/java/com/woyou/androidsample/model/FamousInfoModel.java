package com.woyou.androidsample.model;

import android.content.Context;

import com.woyou.androidsample.RetrofitWrapper;
import com.woyou.androidsample.bean.FamousInfo;
import com.woyou.androidsample.bean.FamousInfoReq;
import com.woyou.androidsample.bean.Result;
import com.woyou.androidsample.netApi.FamousService;

import retrofit2.Call;
import rx.Observable;

/**
 * Created by Xiho on 2016/3/14.
 */
public class FamousInfoModel {
    private static FamousInfoModel famousInfoModel;
    private FamousService mFamousService;

    /**
     * 单例模式
     *
     * @return
     */
    public static FamousInfoModel getInstance(Context context) {
        if (famousInfoModel == null) {
            famousInfoModel = new FamousInfoModel(context);
        }
        return famousInfoModel;
    }


    private FamousInfoModel(Context context) {
        mFamousService = (FamousService) RetrofitWrapper.getInstance().create(FamousService.class);
    }

    /**
     * 查询名人名言
     *
     * @param famousInfoReq
     * @return
     */
    public Call<FamousInfo> queryLookUp(FamousInfoReq famousInfoReq) {
        Call<FamousInfo> infoCall = mFamousService.getFamousResult(famousInfoReq.apiKey, famousInfoReq.keyword, famousInfoReq.page, famousInfoReq.rows);
        return infoCall;
    }

    /**
     * 查询微信精选
     *
     * @param
     * @return
     */
    public Observable<Result> queryNews() {
        Observable<Result> infoCall = mFamousService.getNewsResult(0,20);
        return infoCall;
    }
}
