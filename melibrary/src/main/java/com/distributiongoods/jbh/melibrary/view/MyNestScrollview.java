package com.distributiongoods.jbh.melibrary.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by zbsdata on 2018/8/23.
 */

public class MyNestScrollview extends NestedScrollView implements NestedScrollView.OnScrollChangeListener{

    /**最小滚动的距离*/
    private int minScroll=50;
    /**是否滚动到了顶部*/
    private boolean isScrollviewToTop;
    /**是否滚到了底部*/
    private boolean isScrollviewToBottom;
    /**是否在向上滚动*/
    private boolean isScrollviewUp;
    /**是否在向下滚动*/
    private boolean isScrollviewDown;


    public MyNestScrollview(@NonNull Context context) {
        this(context,null);
    }

    public MyNestScrollview(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNestScrollview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnScrollChangeListener(this);
    }


    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        /*用于判断是否向上或者向下滑动*/
        if (scrollY>oldScrollY){
            scrollViewStateListener.onScrollToUp();
            isScrollviewUp=true;
            isScrollviewDown=false;
        }else {
            scrollViewStateListener.onScrollToDown();
            isScrollviewUp=false;
            isScrollviewDown=true;
        }

        if(v.getScrollY()==0){
            /*滚动到了顶部了*/
            isScrollviewToTop=true;
            isScrollviewToBottom=false;
            scrollViewStateListener.onTop();
        }else if(v.getHeight()+v.getScrollY()-v.getPaddingTop()-v.getPaddingBottom()-v.getChildAt(0).getHeight() == 0){
            isScrollviewToBottom=true;
            isScrollviewToTop=false;
            scrollViewStateListener.onToBottom();
        }else {
            isScrollviewToTop=false;
            isScrollviewToBottom=false;
        }
        scrollViewStateListener.onScrollChange( scrollX,  scrollY,  oldScrollX,  oldScrollY);
    }


    private OnScrollViewStateListener scrollViewStateListener;
    public interface OnScrollViewStateListener{
        void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
        void onTop();
        void onToBottom();
        void onScrollToUp();
        void onScrollToDown();
    }


    public void setScrollViewStateListener(OnScrollViewStateListener scrollViewStateListener) {
        this.scrollViewStateListener = scrollViewStateListener;
    }
}
