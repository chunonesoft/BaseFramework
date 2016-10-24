package com.chunsoft.baseframework.mvp.my.presenter;

import com.chunsoft.baseframework.base.BasePresenterImpl;
import com.chunsoft.baseframework.common.Api;
import com.chunsoft.baseframework.mvp.my.model.FeedbackBean;
import com.chunsoft.baseframework.mvp.my.model.LoginModel;
import com.chunsoft.baseframework.mvp.my.model.LoginNetwork;
import com.chunsoft.baseframework.mvp.my.view.LoginView;
import com.chunsoft.baseframework.network.NetCallback;
import com.chunsoft.baseframework.network.SubscriberCallback;

/**
 * Developer：chunsoft on 2016/10/23 23:34
 * Email：chun_soft@qq.com
 * Content：
 */

public class LoginPersenterImpl extends BasePresenterImpl<LoginView> {
    public LoginModel mLoginModel = LoginNetwork.getRetrofit(Api.BASEURL).create(LoginModel.class);

    public LoginPersenterImpl(LoginView loginView)
    {
        attachView(loginView);
    }
    /**
     * 登录验证
     */
    public void Login(String username,String password)
    {
        View.showProgress();
        addSubscription(mLoginModel.getLogin(username,password),
                new SubscriberCallback<>(new NetCallback<FeedbackBean>() {
                    @Override
                    public void onSuccess(FeedbackBean model) {
                        if (model.retcode.equals("1"))
                        {
                            View.toMainActivity();
                            View.hideProgress();
                        }else {
                            View.showLoadFailMsg("密码不正确");
                        }
                    }

                    @Override
                    public void onFailer(int code, String msg) {
                        View.hideProgress();
                        View.showLoadFailMsg(msg);
                    }

                    @Override
                    public void onCompleted() {
                        View.hideProgress();
                    }
                }));
    }


}
