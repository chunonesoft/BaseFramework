package com.chunsoft.baseframework.network;

/**
 * Developer：chunsoft on 2016/10/23 23:47
 * Email：chun_soft@qq.com
 * Content：网络连接反馈接口
 */

public interface NetCallback<T> {
    void onSuccess(T model);
    void onFailer(int code,String msg);
    void onCompleted();
}
