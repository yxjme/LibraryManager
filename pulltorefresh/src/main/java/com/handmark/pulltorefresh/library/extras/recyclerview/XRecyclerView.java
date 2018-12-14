package com.handmark.pulltorefresh.library.extras.recyclerview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.bumptech.glide.Glide;

/**
 * Created by zbsdata on 2017/5/10.
 */

public class XRecyclerView extends RecyclerView {

    private static final int INVALID_POINTER = -1;
    private int mScrollPointerId = INVALID_POINTER;
    private int mInitialTouchX, mInitialTouchY;
    private int mTouchSlop;


    public XRecyclerView(Context context) {
        this(context, null);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop();
        addOnScrollListener(new ImageAutoLoadScrollListener());
    }

    @Override
    public void setScrollingTouchSlop(int slopConstant) {
        super.setScrollingTouchSlop(slopConstant);
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        switch (slopConstant) {
            case TOUCH_SLOP_DEFAULT:
                mTouchSlop = vc.getScaledTouchSlop();
                break;
            case TOUCH_SLOP_PAGING:
                mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(vc);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        final int action = MotionEventCompat.getActionMasked(e);
        final int actionIndex = MotionEventCompat.getActionIndex(e);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mScrollPointerId = MotionEventCompat.getPointerId(e, 0);
                mInitialTouchX = (int) (e.getX() + 0.5f);
                mInitialTouchY = (int) (e.getY() + 0.5f);
                return super.onInterceptTouchEvent(e);

            case MotionEventCompat.ACTION_POINTER_DOWN:
                mScrollPointerId = MotionEventCompat.getPointerId(e, actionIndex);
                mInitialTouchX = (int) (MotionEventCompat.getX(e, actionIndex) + 0.5f);
                mInitialTouchY = (int) (MotionEventCompat.getY(e, actionIndex) + 0.5f);
                return super.onInterceptTouchEvent(e);

            case MotionEvent.ACTION_MOVE: {
                final int index = MotionEventCompat.findPointerIndex(e, mScrollPointerId);
                if (index < 0) {
                    return false;
                }

                final int x = (int) (MotionEventCompat.getX(e, index) + 0.5f);
                final int y = (int) (MotionEventCompat.getY(e, index) + 0.5f);
                if (getScrollState() != SCROLL_STATE_DRAGGING) {
                    final int dx = x - mInitialTouchX;
                    final int dy = y - mInitialTouchY;
                    final boolean canScrollHorizontally = getLayoutManager().canScrollHorizontally();
                    final boolean canScrollVertically = getLayoutManager().canScrollVertically();
                    boolean startScroll = false;
                    if (canScrollHorizontally && Math.abs(dx) > mTouchSlop && (Math.abs(dx) >= Math.abs(dy) || canScrollVertically)) {
                        startScroll = true;
                    }
                    if (canScrollVertically && Math.abs(dy) > mTouchSlop && (Math.abs(dy) >= Math.abs(dx) || canScrollHorizontally)) {
                        startScroll = true;
                    }
                    return startScroll && super.onInterceptTouchEvent(e);
                }
                return super.onInterceptTouchEvent(e);
            }

            default:
                return super.onInterceptTouchEvent(e);
        }
    }

    //监听滚动来对图片加载进行判断处理
    public class ImageAutoLoadScrollListener extends OnScrollListener{

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState){
                case SCROLL_STATE_IDLE:
                    // The RecyclerView is not currently scrolling.
                    //当屏幕停止滚动，加载图片
                    try {
                        if(getContext() != null) Glide.with(getContext()).resumeRequests();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SCROLL_STATE_DRAGGING:
                    // The RecyclerView is currently being dragged by outside input such as user touch input.
                    //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                case SCROLL_STATE_SETTLING:
                    // The RecyclerView is currently animating to a final position while not under outside control.
                    //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                    try {
                        if(getContext() != null) Glide.with(getContext()).pauseRequests();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}