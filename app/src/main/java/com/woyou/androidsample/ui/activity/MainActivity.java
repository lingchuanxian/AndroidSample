package com.woyou.androidsample.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.woyou.androidsample.Constant;
import com.woyou.androidsample.R;
import com.woyou.androidsample.bean.FamousInfoReq;
import com.woyou.androidsample.bean.Result;
import com.woyou.androidsample.model.FamousInfoModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    public final String TAG=this.getClass().getName();
    private FamousInfoModel famousInfoModel;
    private EditText mEditKeyWord;
    private ImageButton mSerachBtn;
    private TextView mTxtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        famousInfoModel =FamousInfoModel.getInstance(getApplicationContext());
        initView();
        initParams();
        //initEvent();
        initWeChat();

    }

    private void initWeChat() {
       /* Call<Result> ResultCall = famousInfoModel.queryNews();
        ResultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.e("result",response.toString());
                if(response.isSuccess()){
                    Result result = response.body();
                    Log.e("Result-result",result.toString());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });*/

        famousInfoModel.queryNews()
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .doOnNext(new Action1<Result>(){
                    @Override
                    public void call(Result result) {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        Log.e("result", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("result", e.getMessage());
                    }

                    @Override
                    public void onNext(Result result) {
                        for (Result.SubjectsBean  sub:result.getSubjects()){
                            Log.d("电影名：",sub.getTitle());
                        }
                        //Log.e("result", result.toString());
                    }
                });

    }

    /**
     * 初始化View
     */
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEditKeyWord = (EditText) findViewById(R.id.edit_keyword);
        mSerachBtn = (ImageButton) findViewById(R.id.button_search);
        mTxtContent = (TextView) findViewById(R.id.txt_content);

    }

    /**
     * 初始化参数
     */
    private FamousInfoReq initParams() {
        FamousInfoReq mFamousInfoReq=null;
        mFamousInfoReq= new FamousInfoReq();
        mFamousInfoReq.apiKey= Constant.APIKEY;
        mFamousInfoReq.keyword =mEditKeyWord.getText().toString();
        mFamousInfoReq.page=3;
        mFamousInfoReq.rows=30;
        return  mFamousInfoReq;

    }
/*
    // 获取事件
    private void initEvent() {
        mSerachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建访问的API请求
                Call<FamousInfo> callFamous= famousInfoModel.queryLookUp(initParams());
                //发送请求
                callFamous.enqueue(new Callback<FamousInfo>() {
                    @Override
                    public void onResponse(Call<FamousInfo> call, Response<FamousInfo> response) {
                        if(response.isSuccess()){
                            FamousInfo result = response.body();
                            if(result!=null){
                                Log.e("result",result.toString());
                                List<FamousInfo.ResultEntity> entity = result.getResult();
                                mTxtContent.setText("1、"+entity.get(0).getFamous_saying()+"\n---"+entity.get(0).getFamous_name()+"\n 2、"
                                        +entity.get(1).getFamous_saying()+"\n---"+entity.get(1).getFamous_name());
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<FamousInfo> call, Throwable t) {

                    }
                });
            }
        });
    }*/
}
