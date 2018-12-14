package com.distributiongoods.jbh.melibrary.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zbsdata on 2018/8/24.
 */

public class MyItemDecoration  extends RecyclerView.ItemDecoration{


    public int mHeight=2;
    private Paint paint;
    int lineColor = Color.GRAY;

    public MyItemDecoration() {
        super();
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(lineColor);
    }


    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        paint.setColor(lineColor);
    }


    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }



    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position=parent.getChildAdapterPosition(view);
        if(position!=0){
            outRect.top=mHeight;
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        /*绘制分割线  左边的距离*/
        int left=parent.getLeft();
        /*右边的距离*/
        int right=parent.getRight();
        int childCount=parent.getChildCount();
        for (int i=0;i<childCount;i++){
            View view=parent.getChildAt(i);
            int bottom=view.getTop();
            int top=bottom-mHeight;
            c.drawRect(new Rect(left,top,right,bottom),paint);
        }
    }
}
