package com.chunsoft.baseframework.mvp.my.model;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Developer：chunsoft on 2016/10/23 22:27
 * Email：chun_soft@qq.com
 * Content：
 */

public class LoginNetwork {
    public static Retrofit mRetrofit;

    public static Retrofit getRetrofit(String baseUrl)
    {
        if (mRetrofit ==null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (true){
                //HttpLoggingInterceptor是okhttp提供的日志系统，打印网络请求和返回，
                // GsonConverterFactory转换器
                //在创建service实例之前添加一个CallAdapter进Retrofit对象
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }
}
