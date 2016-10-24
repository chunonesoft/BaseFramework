package com.chunsoft.baseframework.mvp.my.model;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Developer：chunsoft on 2016/10/23 22:06
 * Email：chun_soft@qq.com
 * Content：
 */

public interface LoginModel {
    @GET("users/login_get.do")
    Observable<FeedbackBean> getLogin(@Query("username")String username,@Query("password")String password);
}
