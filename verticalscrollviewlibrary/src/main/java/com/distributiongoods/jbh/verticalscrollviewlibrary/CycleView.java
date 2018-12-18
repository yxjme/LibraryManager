package com.distributiongoods.jbh.verticalscrollviewlibrary;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;
import java.util.List;

/**
 * Created by zbsdata on 2018/10/9.
 */

public class CycleView extends ViewFlipper {

    private int inAnim = R.anim.anim_in;
    private int outAnim = R.anim.anim_out;
    private int itemViewCount;
    /*切换的间隔时间*/
    private int flipInterval = 3000;

    public CycleView(Context context) {
        this(context,null);
    }

    public CycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFlipInterval(flipInterval);
        setInAnimation(AnimationUtils.loadAnimation(context,inAnim));
        setOutAnimation(AnimationUtils.loadAnimation(context,outAnim));


        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CycleView);
        int count=array.getIndexCount();
        for (int i=0;i<count ; i++){
            int attr=array.getIndex(i);
            if (attr == R.styleable.CycleView_inAnim) {
                inAnim = array.getInt(attr, android.R.anim.slide_in_left);
                setInAnimation(AnimationUtils.loadAnimation(context, inAnim));

            } else if (attr == R.styleable.CycleView_outAnim) {
                outAnim = array.getInt(attr, android.R.anim.slide_out_right);
                setOutAnimation(AnimationUtils.loadAnimation(context, outAnim));

            } else if (attr == R.styleable.CycleView_flipInterval) {
                flipInterval = array.getInt(attr, 3000);
                setFlipInterval(flipInterval);
            }
        }
        array.recycle();
    }




    /**
     * @param inAnim
     */
    public void setInAnim(int inAnim) {
        this.inAnim = inAnim;
        setInAnimation(AnimationUtils.loadAnimation(getContext(),inAnim));
    }


    /**
     * @param outAnim
     */
    public void setOutAnim(int outAnim) {
        this.outAnim = outAnim;
        setOutAnimation(AnimationUtils.loadAnimation(getContext(),outAnim));
    }



    /**
     *
     * @param string
     */
    public void setDataString(List<String> string){
        if (string!=null)itemViewCount=string.size();
        for (String s :string){
            TextView view= (TextView) LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1,this,false);
            view.setText(s);
            addView(view);
        }
        autoStart();
    }





    /**
     *
     * @param views
     */
    public void setDataView(List<View> views){
        if (views!=null)itemViewCount=views.size();
        for (View view:views){
            addView(view);
        }
        autoStart();
    }



    /**
     *
     */
    public void autoStart(){
        if (itemViewCount>1){
            this.startFlipping();
            setAutoStart(true);
        }
    }




    public void setAdapter(){


    }


}
