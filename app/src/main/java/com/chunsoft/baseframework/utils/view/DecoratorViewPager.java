package com.chunsoft.baseframework.utils.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Developer：chunsoft on 2016/10/31 16:04
 * Email：chun_soft@qq.com
 * Content：viewPager和RecycleView相互冲突，将父View传到ViewPager里面
 * 使用父类方法
 */

public class DecoratorViewPager extends ViewPager{
    private ViewGroup parent;
    public DecoratorViewPager(Context context) {
        super(context);
    }

    public DecoratorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNestedpParent(ViewGroup parent) {
        this.parent = parent;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (parent != null){
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (parent != null){
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (parent != null){
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(ev);
    }
}
