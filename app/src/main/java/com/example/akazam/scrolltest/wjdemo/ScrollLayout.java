package com.example.akazam.scrolltest.wjdemo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * TODO: document your custom view class.
 */
public class ScrollLayout extends LinearLayout {
    public static final String TAG = "ScrollLayout";

    ViewDragHelper dragHelper;
    private View mTop, mNav;
    ViewGroup mBottom;
    private View curView;
    private int tH, cH, bH;
    private boolean canPullUp = false;

    public ScrollLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        dragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {//当前view是否允许拖动
                curView = child;
//                if (child==mBottom)return false;
                return true;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {//实现竖向拖动
                //控制下拉多出部分，回归到不下拉状态
                if (curView == mTop && top > 0) {
                    return 0;
                } else if (curView == mNav && top > tH) {
                    return tH;
                } else if (curView == mBottom) {
                    if (top > (tH + cH)) {
                        return tH + cH;
                    } else if (top < cH) {
                        return cH;
                    }
                }
                //向上滑动任意控件，实现控件的连动
                changeHeight();
                return top;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {//TODO 实现横向滑动
                changeWidth();
                return super.clampViewPositionHorizontal(child, left, dx);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {//ACTION_UP事件后调用此方法
                //根据速度，设置动画
                if (releasedChild == mBottom) {
                    changeHeight();
                    if (yvel > 10) {
                        dragHelper.settleCapturedViewAt(0, tH);
                    }
                    if (yvel < -10) {
                        dragHelper.settleCapturedViewAt(0, cH);
                    }
                    postInvalidate();
                }
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                //view在拖动过程坐标发生变化时会调用此方法，包括两个时间段：手动拖动和自动滚动

                changeHeight();
            }

            @Override
            public void onViewDragStateChanged(int state) {
                switch (state) {
                    case ViewDragHelper.STATE_IDLE:
                        if (mNav.getTop() <= 0) {
                            mNav.layout(0, 0, getWidth(), cH);
                            mBottom.layout(0, cH, getWidth(), getHeight());
                        } else {
                            mNav.layout(0, mTop.getBottom(), getWidth(), mTop.getBottom() + cH);
                            mBottom.layout(0, mTop.getBottom() + cH, getWidth(), getHeight());
                        }
                        break;
                    case ViewDragHelper.STATE_DRAGGING:
                        changeHeight();
                        break;
                }
            }
        });
    }

    private void changeWidth() {
        int w = getWidth();
        int h = getHeight();
        int childCount = mBottom.getChildCount();
        RecyclerView recyclerView = (RecyclerView) ((ViewPager) mBottom).getChildAt(1);
        if (curView == mBottom) {
            mTop.layout(0, 0, w, tH);
            mNav.layout(0, tH, w, tH + cH);
            mBottom.layout(w, tH + cH, 2 * w, h);
        }
    }

    /**
     * 向上滑动任何一个控件，实现三个控件之间的连动
     */
    private void changeHeight() {
        Log.d(TAG, "changeHeight: " + mNav.getTop());
        int w = getWidth();
        int h = getHeight();
        if (curView == mTop) {
//            mTop.layout(0, mTop.getTop(), w, mTop.getBottom());
            mNav.layout(0, mTop.getBottom(), w, mTop.getBottom() + cH);
            mBottom.layout(0, mTop.getBottom() + cH, w, h);
        } else if (curView == mNav) {
//            mNav.layout(0, mNav.getTop(), w, mNav.getBottom());
            mTop.layout(0, mNav.getTop() - tH, w, mNav.getTop());
            mBottom.layout(0, mNav.getBottom(), w, h);
        } else if (curView == mBottom) {
            mTop.layout(0, mBottom.getTop() - cH - tH, w, mBottom.getTop() - cH);
            mNav.layout(0, mBottom.getTop() - cH, w, mBottom.getTop());
            mBottom.layout(0, mBottom.getTop(), w, h);
        }

        int cc = ((ViewPager) mBottom).getChildCount();
        for (int i = 0; i < cc; i++) {
            View c = mBottom.getChildAt(i);
            c.layout(c.getLeft(), 0, c.getRight(), mBottom.getHeight());
        }
    }

    float x1 = 0, y1 = 0, x2 = 0, y2 = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {//true:外部大的拦截，false:内部listView拦截
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
            Log.d(TAG, "onInterceptTouchEvent: ACTION_DOWN  x1---" + x1 + "----y1----" + y1);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            Log.d(TAG, "onInterceptTouchEvent: IIIIIIIIIIIIIIIIIii ACTION_UP  x2---" + x2 + "----y2----" + y2);
            if (y1 - y2 > 50) {
            } else if (y2 - y1 > 50) {

            } else if (x1 - x2 > 50) {
                Log.d(TAG, "onTouchEvent: 左滑");
                return false;
            } else if (x2 - x1 > 50) {
                Log.d(TAG, "onTouchEvent: 右滑");
                return false;
            }
        }
        if (mNav.getTop() <= 0) {
            if (event.getHistorySize() > 0) {                         //拦截Touch事件
                if (event.getY() > event.getHistoricalY(0)) {         //向下滑动
                    RecyclerView view3 = (RecyclerView) ((ViewPager) mBottom).getChildAt(1);
                    int Offset = view3.computeVerticalScrollOffset();
                    Log.d(TAG, "onInterceptTouchEvent: 1");
                    if (Offset <= 0) {
                        Log.d(TAG, "onInterceptTouchEvent: 2");
                        dragHelper.processTouchEvent(event);
                        return true;
                    } else {
                        Log.d(TAG, "onInterceptTouchEvent: 3");
                        return false;
                    }
                } else if (event.getY() < event.getHistoricalY(0)) {  //向上滑动
                    return false;
                } else if (event.getY() == event.getHistoricalY(0)) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
            Log.d(TAG, "onInterceptTouchEvent: ACTION_DOWN  x1---" + x1 + "----y1----" + y1);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            Log.d(TAG, "onInterceptTouchEvent: ACTION_UP  x2---" + x2 + "----y2----" + y2);
            if (y1 - y2 > 50) {
            } else if (y2 - y1 > 50) {

            } else if (x1 - x2 > 50) {
                Log.d(TAG, "onTouchEvent: 左滑");
                return false;
            } else if (x2 - x1 > 50) {
                Log.d(TAG, "onTouchEvent: 右滑");
                return false;
            }
        }
        if (mNav.getTop() <= 0) {
            if (event.getHistorySize() > 0) {                         //拦截Touch事件
                if (event.getY() > event.getHistoricalY(0)) {         //向下滑动
                    RecyclerView view3 = (RecyclerView) ((ViewPager) mBottom).getChildAt(1);
                    int Offset = view3.computeVerticalScrollOffset();
                    Log.d(TAG, "onInterceptTouchEvent: 111111");
                    if (Offset <= 0) {
                        Log.d(TAG, "onInterceptTouchEvent: 22222222");
                        dragHelper.processTouchEvent(event);
                        return true;
                    } else {
                        Log.d(TAG, "onInterceptTouchEvent: 333333333");
                        return false;
                    }
                } else if (event.getY() < event.getHistoricalY(0)) {  //向上滑动
                    return false;
                } else if (event.getY() == event.getHistoricalY(0)) {
                    return false;
                }
            }
            return false;
        }
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTop = getChildAt(0);
        mNav = getChildAt(1);
        mBottom = (ViewGroup) getChildAt(2);
        tH = mTop.getHeight();
        cH = mNav.getHeight();
        bH = mBottom.getHeight();

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true)) {
            changeHeight();
            postInvalidate();
//            invalidate();
        }
    }

}
