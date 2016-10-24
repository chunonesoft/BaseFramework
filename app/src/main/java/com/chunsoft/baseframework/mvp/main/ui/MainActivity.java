package com.chunsoft.baseframework.mvp.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.chunsoft.baseframework.R;
import com.chunsoft.baseframework.base.BaseActivity;
import com.chunsoft.baseframework.mvp.group.ui.GroupFragment;
import com.chunsoft.baseframework.mvp.home.ui.HomeFragment;
import com.chunsoft.baseframework.mvp.main.presenter.MainPresenterImpl;
import com.chunsoft.baseframework.mvp.main.view.MainView;
import com.chunsoft.baseframework.mvp.message.ui.MessageFragment;
import com.chunsoft.baseframework.mvp.my.ui.LoginActivity;
import com.chunsoft.baseframework.mvp.my.ui.MyFragment;

public class MainActivity extends BaseActivity<MainPresenterImpl>
        implements MainView ,BottomNavigationBar.OnTabSelectedListener{
    private BottomNavigationBar mBottomBar;
    private HomeFragment homeFragment;
    private GroupFragment groupFragment;
    private MessageFragment messageFragment;
    private MyFragment myFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter.switchNavigation();
    }

    @Override
    protected MainPresenterImpl createPresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    //设置BottomNavigationBar
    @Override
    public void SwitchItem() {
        mBottomBar = (BottomNavigationBar) findViewById(R.id.bottom_bar);
        mBottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomBar.setBackgroundColor(getResources().getColor(R.color.light_gray));

        mBottomBar
                .addItem(new BottomNavigationItem(R.drawable.home_guide_01, "首页"))
                .setActiveColor(R.color.backBottom).setInActiveColor(R.color.backBottom_in)
                .addItem(new BottomNavigationItem(R.drawable.home_guide_02, "社区"))
                .setActiveColor(R.color.backBottom).setInActiveColor(R.color.backBottom_in)
                .addItem(new BottomNavigationItem(R.drawable.home_guide_03, "消息"))
                .setActiveColor(R.color.backBottom).setInActiveColor(R.color.backBottom_in)
                .addItem(new BottomNavigationItem(R.drawable.home_guide_04, "我的"))
                .setActiveColor(R.color.backBottom).setInActiveColor(R.color.backBottom_in)
                .initialise();
        mBottomBar.setTabSelectedListener(this);//设置监听
        setDefaultFragment();//设置默认选项

    }
    //设置默认界面
    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.ll_root,homeFragment);
        fragmentTransaction.commit();
    }
    //隐藏所有界面
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (groupFragment != null) {
            transaction.hide(groupFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);

        switch (position)
        {
            case 0:
                if (homeFragment == null)
                {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.ll_root,homeFragment);
                }else {
                    fragmentTransaction.show(homeFragment);
                }
                break;
            case 1:
                if (groupFragment == null)
                {
                    groupFragment = new GroupFragment();
                    fragmentTransaction.add(R.id.ll_root,groupFragment);
                }else {
                    fragmentTransaction.show(groupFragment);
                }
                break;
            case 2:
                if (messageFragment == null)
                {
                    messageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.ll_root,messageFragment);
                }else {
                    fragmentTransaction.show(messageFragment);
                }
                break;
            case 3:
                if (myFragment == null)
                {
                    myFragment = new MyFragment();
                    fragmentTransaction.add(R.id.ll_root,myFragment);
                }else {
                    fragmentTransaction.show(myFragment);
                }
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
