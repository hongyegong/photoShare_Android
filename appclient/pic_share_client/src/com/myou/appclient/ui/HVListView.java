package com.myou.appclient.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 自定义支持横向滚动的ListView
 *
 */
public class HVListView extends ListView {

    /** 手势 */
    private GestureDetector mGesture;
    /** 偏移坐标 */
    private int mOffset = 0;
    /** 屏幕宽度 */
    private int screenWidth;

    /** 构造函数 */
    public HVListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mGesture = new GestureDetector(context, mOnGesture);
    }

    /** 分发触摸事件 */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    super.dispatchTouchEvent(ev);
    return mGesture.onTouchEvent(ev);
    }

    /** 手势 */
    private OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
        float velocityY) {
        return false;
    }

    /** 滚动 */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
        float distanceX, float distanceY) {
        synchronized (HVListView.this) {
        int moveX = (int) distanceX;
        int scrollWidth = getWidth();
        int dx = moveX;
        //控制越界问题
        if ( moveX < 0)
            dx = 0;
        if (moveX + getScreenWidth() > scrollWidth)
            dx = scrollWidth - getScreenWidth();

        mOffset += dx;
        //根据手势滚动Item视图
        for (int i = 0, j = getChildCount(); i < j; i++) {
            View child = ((ViewGroup) getChildAt(i)).getChildAt(1);
            if (child.getScrollX() != mOffset)
            child.scrollTo(mOffset, 0);
        }
        }
        requestLayout();
        return true;
    }
    };

    
    /**
     * 获取屏幕可见范围内最大屏幕
     * @return
     */
    public int getScreenWidth() {
    if (screenWidth == 0) {
        screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        if (getChildAt(0) != null) {
        screenWidth -= ((ViewGroup) getChildAt(0)).getChildAt(0)
            .getMeasuredWidth();
        } 
    }
    return screenWidth;
    }

}
