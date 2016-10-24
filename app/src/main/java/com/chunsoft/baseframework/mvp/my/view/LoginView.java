package com.chunsoft.baseframework.mvp.my.view;

/**
 * Developer：chunsoft on 16/6/14 00:29
 * Email：chun_soft@qq.com
 * Content：Presenter与View交互通过接口
 * View接口编写，观察功能上的操作，然后考虑，
 * 1.该操作需要什么？（getUserName,getPassword）
 * 2.该操作的结果对应的反馈？(toMainActivity)
 * 3.该操作过程中的友好的交互？(showProgress,showLoadFailMsg)
 * 如登录按钮对应用户名、密码获取；友好提示对应进度显示等
 */
public interface LoginView {
    String getUserName();
    String getPassword();
    void showProgress();
    void toMainActivity();
    void hideProgress();
    void showLoadFailMsg(String msg);
}