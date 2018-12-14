package com.distributiongoods.jbh.melibrary.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by zbsdata on 2018/9/14.
 */

public class MyItemD extends RecyclerView.ItemDecoration{

    private ISticky mISticky;
    //矩形高度
    private int mRectHeight;
    //文字TextSize
    private int mTextPaintSize;
    private Paint mTxtPaint;
    private Paint mRectPaint;
    //分割线画笔
    private Paint mDividerPaint;
    //手机状态栏的高度
    private int mStatusBarHeight;

    public MyItemD(Context context, int statusBarHeight, ISticky iSticky) {
        mStatusBarHeight=statusBarHeight;
        mISticky=iSticky;
        mRectHeight= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,30, context.getResources().getDisplayMetrics());
        mTextPaintSize=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,17, context.getResources().getDisplayMetrics());
        mTxtPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTxtPaint.setColor(Color.BLACK);
        mTxtPaint.setTextSize(mTextPaintSize);
        mRectPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setStyle(Paint.Style.FILL);
        mRectPaint.setColor(Color.parseColor("#DDDDDD"));
        mDividerPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setStyle(Paint.Style.FILL);
        mDividerPaint.setColor(Color.WHITE);
    }




    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        super.onDraw(c, parent, state);
        int childCount=parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view=parent.getChildAt(i);
            int left=parent.getPaddingLeft();
            int right=parent.getWidth()-parent.getPaddingRight();
            int top=view.getTop()-1;
            int bottom=view.getTop();

            //Item分割线
            c.drawRect(left,top,right,bottom,mDividerPaint);
        }
    }




    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        /*两个都是获取布局的中item数量*/
        int childCount=parent.getChildCount();
        int itemCount=state.getItemCount();

        /*左边的距离*/
        int left=parent.getLeft();
        /*右边距*/
        int right=parent.getWidth()-parent.getPaddingRight();

        /**/
        String preGroupTitle;  //
        String groupTitle=""; // 主标题


        for (int i = 0; i < childCount; i++) {
            View child=parent.getChildAt(i);
            int pos=parent.getChildAdapterPosition(child);


            /*值得学习的交换方法*/
            preGroupTitle=groupTitle;
            groupTitle=mISticky.getGroupTitle(pos);

            //如果当前分组名和之前分组名一样，忽略此次循环
            if (groupTitle.equals(preGroupTitle)) {
                continue;
            }


            //文字的基线，保证显示完全
            int textBaseLine=Math.max(mRectHeight,child.getTop());
            //分组标题
            String title=mISticky.getGroupTitle(pos);



            /*主要实现吧当前吸附在顶部的item往上顶*/
            int viewBottom=child.getBottom();
            //加入限定 防止数组越界
            if (pos + 1 < itemCount) {
                String nextGroupTitle=mISticky.getGroupTitle(pos+1);
                //当分组不一样  并且改组要向上移动时候
                if (!nextGroupTitle.equals(groupTitle) && viewBottom < textBaseLine) {
                    //将上一个往上移动
                    textBaseLine = viewBottom;
                }
            }


            //绘制边框
            c.drawRect(left, textBaseLine - mRectHeight, right, textBaseLine, mRectPaint);
            //文字的高度
            int value= (int) Math.abs(mTxtPaint.getFontMetrics().descent +mTxtPaint.getFontMetrics().ascent);
            /*绘制显示文本标题*/
            c.drawText(title, left, textBaseLine-((mRectHeight-value)/2), mTxtPaint);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        /*两者的大部分的时候值都是相同的 */
         /*获取当前view在适配器中的索引*/
//        int posparent.getChildAdapterPosition(view);
        /*获取当前view在布局中的索引*/
        int pos=parent.getChildLayoutPosition(view);

        if (mISticky.isFirstPosition(pos)) {
            /*为新组预留空间*/
            outRect.top=mRectHeight;
        }
        /*分割线预留空间*/
        outRect.bottom=1;
    }
}
