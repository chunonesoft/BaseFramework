package com.chunsoft.baseframework.network.home;

import com.chunsoft.baseframework.mvp.home.model.HomeModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Developer：chunsoft on 2016/10/27 00:25
 * Email：chun_soft@qq.com
 * Content：Model层接口
 */

public interface HomeService {
    //获取数据
    @GET("api/v31/channels/{channels_id}")
    Observable<HomeModel> getHomeList(@Path("channels_id") int channels_id);


     //获取下一页数据
    @GET("api/v31/channels/{channels_id}")
    Observable<HomeModel> getNextHomeList(@Path("channels_id") int channels_id);

}
