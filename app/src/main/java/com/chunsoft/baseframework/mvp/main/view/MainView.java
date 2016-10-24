package com.chunsoft.baseframework.mvp.main.view;

/**
 * Developer：chunsoft on 2016/10/16 20:43
 * Email：chun_soft@qq.com
 * Content：
 * view层的接口，观察功能上的操作考虑，
 * 该操作需要什么？（getUserName, getPassword）
 * 该操作的结果，对应的反馈？(toMainActivity, showFailedError)
 * 该操作过程中对应的友好的交互？(showLoading, hideLoading)
 */

public interface MainView {
    //选择点击项目
    void SwitchItem();
}
