package com.chunsoft.baseframework.mvp.my.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chunsoft.baseframework.R;
import com.chunsoft.baseframework.base.BaseActivity;
import com.chunsoft.baseframework.mvp.main.ui.MainActivity;
import com.chunsoft.baseframework.mvp.my.presenter.LoginPersenterImpl;
import com.chunsoft.baseframework.mvp.my.view.LoginView;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPersenterImpl> implements LoginView,View.OnClickListener{
    // UI references.
    @BindView(R.id.et_mobile)
    EditText et_mobile;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.btn_login)
    Button btn_login;

    private ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_login.setOnClickListener(this);
    }

    @Override
    protected LoginPersenterImpl createPresenter() {
        return new LoginPersenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

    }


    @Override
    public String getUserName() {
        return et_mobile.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void showProgress() {
        if(dialog == null)
        {
            dialog = ProgressDialog.show(LoginActivity.this,
                    "", "正在加载...");
            dialog.show();
        }

    }

    @Override
    public void toMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        this.startActivity(intent);
    }



    @Override
    public void hideProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void showLoadFailMsg(String msg) {
        Toast.makeText(this,
                msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_login:
                presenter.Login(getUserName(),getPassword());
                break;
        }
    }
}
