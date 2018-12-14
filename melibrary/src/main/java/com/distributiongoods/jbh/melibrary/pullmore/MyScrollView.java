package com.distributiongoods.jbh.melibrary.pullmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by zbsdata on 2018/7/17.
 */

public class MyScrollView extends ScrollView {

    private static String TAG = MyScrollView.class.getName();

    public void setScrollListener(ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }
    private ScrollListener mScrollListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mScrollListener != null) {
                    /*滚动视图第一个子view的高度*/
                    int contentHeight = getChildAt(0).getHeight();
                    /*滚动视图的高度*/
                    int scrollHeight = getHeight();
                    /*滚动的距离*/
                    int scrollY = getScrollY();
                    /**/
                    mScrollListener.onScroll(scrollY);

                    if (scrollY + scrollHeight >= contentHeight || contentHeight <= scrollHeight) {
                        mScrollListener.onScrollToBottom();
                    } else {
                        mScrollListener.notBottom();
                    }

                    if (scrollY == 0) {
                        mScrollListener.onScrollToTop();
                    }
                }
                break;
        }
        boolean result = super.onTouchEvent(ev);
        requestDisallowInterceptTouchEvent(false);
        return result;
    }

    public interface ScrollListener {
        void onScrollToBottom();
        void onScrollToTop();
        void onScroll(int scrollY);
        void notBottom();
    }
}