package com.chunsoft.baseframework.mvp.home.view;

import com.chunsoft.baseframework.mvp.home.model.HomeModel;

/**
 * Developer：chunsoft on 2016/10/27 00:19
 * Email：chun_soft@qq.com
 * Content：首页view层接口
 */

public interface HomeListView {
    void showProgress();
    void hideProgress();
    void loadFail(String msg);
    void addData(HomeModel model);
}
