package com.chunsoft.baseframework.mvp.home.presenter;

import com.chunsoft.baseframework.base.BasePresenterImpl;
import com.chunsoft.baseframework.common.Api;
import com.chunsoft.baseframework.mvp.home.model.HomeModel;
import com.chunsoft.baseframework.mvp.home.view.HomeListView;
import com.chunsoft.baseframework.network.CommonNetwork;
import com.chunsoft.baseframework.network.NetCallback;
import com.chunsoft.baseframework.network.SubscriberCallback;
import com.chunsoft.baseframework.network.home.HomeService;

/**
 * Developer：chunsoft on 2016/10/27 00:23
 * Email：chun_soft@qq.com
 * Content：首页Presenter实现
 */

public class HomeListPresenterImpl extends BasePresenterImpl<HomeListView>{
    public HomeService mHomeService = CommonNetwork.getRetrofit(Api.BAOZOU).create(HomeService.class);
    public HomeListPresenterImpl(HomeListView mHomeListView)
    {
        attachView(mHomeListView);
    }


    //默认加载第一页数据
    public void loadData(int channels)
    {
        View.showProgress();
        addSubscription(mHomeService.getHomeList(channels),new SubscriberCallback<>(new NetCallback<HomeModel>() {
            @Override
            public void onSuccess(HomeModel model) {
                View.addData(model);
            }

            @Override
            public void onFailer(int code, String msg) {
                View.loadFail(msg);
            }

            @Override
            public void onCompleted() {
                View.hideProgress();
            }
        }));
    }

    //加载下一页数据
    public void loadNextData(int channels){
        View.showProgress();
        addSubscription(mHomeService.getNextHomeList(channels),
                new SubscriberCallback<>(new NetCallback<HomeModel>() {

            @Override
            public void onSuccess(HomeModel model) {
                View.addData(model);
            }

            @Override
            public void onFailer(int code, String msg) {
                View.loadFail(msg);
            }

            @Override
            public void onCompleted() {
                View.hideProgress();
            }
        }));
    }



}
