package com.chunsoft.baseframework.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity<P extends BasePresenterImpl> extends AppCompatActivity {
    //CompositeSubscription持有Subscriptions
    private CompositeSubscription mCompositeSubscription;
    protected P presenter;

    public final static String TAG = BaseActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //返回presenter
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
        //设置布局文件
        setContentView(getLayoutId());
        ButterKnife.bind(this);

    }

    //添加订阅
    public void addSubscription(Subscription subscription)
    {
        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(subscription);
    }

    //取消所有订阅
    public void onUnsubscribe(){
        if (mCompositeSubscription != null)
        {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
        if (presenter != null){
            presenter.detachView();
        }
    }
}
