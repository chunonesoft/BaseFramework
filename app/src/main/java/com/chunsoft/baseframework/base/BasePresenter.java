package com.chunsoft.baseframework.base;

/**
 * Developer：chunsoft on 2016/10/16 15:48
 * Email：chun_soft@qq.com
 * Content：
 * Presenter是用作Model和View之间交互的桥梁，BasePresenter为Presenter的基类接口
 * 主要包括两个方法，绑定view,和取消view
 */

public interface BasePresenter <V>{
    void attachView(V view);
    void detachView();
}
