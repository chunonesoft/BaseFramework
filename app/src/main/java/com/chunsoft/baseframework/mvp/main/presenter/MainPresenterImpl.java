package com.chunsoft.baseframework.mvp.main.presenter;

import com.chunsoft.baseframework.base.BasePresenterImpl;
import com.chunsoft.baseframework.mvp.main.view.MainView;

/**
 * Developer：chunsoft on 2016/10/16 20:48
 * Email：chun_soft@qq.com
 * Content：主界面Presenter层实现继承基类Presenter层实现
 */

public class MainPresenterImpl extends BasePresenterImpl<MainView>{
    public MainPresenterImpl(MainView mainView){
        attachView(mainView);
    }
    //选择导航
    public void switchNavigation()
    {
        View.SwitchItem();
    }
}
