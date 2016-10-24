package com.chunsoft.baseframework.base;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Developer：chunsoft on 2016/10/16 15:48
 * Email：chun_soft@qq.com
 * Content：
 * Presenter是用作Model和View之间交互的桥梁，BasePresenter为Presenter的基类接口
 * BasePresenterImpl实现BasePresenter基类接口
 */

public class BasePresenterImpl<V> implements BasePresenter<V>{
    public V View;

    //Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)

    //CompositeSubscription来持有所有的Subscriptions
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void attachView(V view) {
        this.View = view;
    }

    @Override
    public void detachView() {
        this.View = null;
        onSubscribe();
    }

    //添加订阅
    public void addSubscription(Observable observable, Subscriber subscriber)
    {
        if (mCompositeSubscription == null)
        {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable.
        subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber));
    }
    //取消所有订阅
    public void onSubscribe()
    {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
        {
            mCompositeSubscription.unsubscribe();
        }
    }
}
