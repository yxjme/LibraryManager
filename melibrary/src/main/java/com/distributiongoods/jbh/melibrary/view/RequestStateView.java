package com.distributiongoods.jbh.melibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.distributiongoods.jbh.melibrary.R;

/**
 * Created by zbsdata on 2018/5/28.
 */

public class RequestStateView extends FrameLayout  implements View.OnClickListener {

    private Context context;
    private String nothingText;
    private Drawable noDataImg;

    private int[] views={
            R.layout.loading_layout,
            R.layout.nothing_layout,
            R.layout.no_network_connect_layout
    };

    public static final int STATE_NONE=0;
    public static final int STATE_LOADING=1;
    public static final int STATE_NOTHING=2;
    public static final int STATE_NOT_NETWORK=3;


    public RequestStateView(Context context) {
        this(context,null);
    }


    public RequestStateView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }


    public RequestStateView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.RequestStateView,0,defStyleAttr);
        int count=array.getIndexCount();
        for (int i=0;i<count;i++){
            int attr = array.getIndex(i);
            if (attr == R.styleable.RequestStateView_state) {
                int state = array.getInt(R.styleable.RequestStateView_state, STATE_NONE);
                setContentView(state);
            } else if (attr == R.styleable.RequestStateView_nothingText) {
                nothingText = array.getString(R.styleable.RequestStateView_nothingText);
            } else if (attr == R.styleable.RequestStateView_noDataImg){
                noDataImg=array.getDrawable(R.styleable.RequestStateView_noDataImg);
            }
        }
        array.recycle();
    }





    /**
     *  初始化View
     *
     *
     * @param state
     */
    public void setContentView(int state) {
        removeAllViews();
        setVisibility(VISIBLE);
        LayoutInflater inflater=LayoutInflater.from(context);
        switch (state){
            case  STATE_NONE:
                setVisibility(GONE);
                break;
            case  STATE_LOADING:
                View loading=inflater.inflate(views[0],null);
                initLoadingView(loading);
                break;
            case  STATE_NOTHING:
                View nothing=inflater.inflate(views[1],null);
                initNothingView(nothing);

                break;
            case  STATE_NOT_NETWORK:
                View network=inflater.inflate(views[2],null);
                initNetworkView(network);
                break;
        }
    }



    /**
     * 没有网络的时候显示布局
     *
     * @param network
     */
    private void initNetworkView(View network) {
        this.addView(network);
        Button button=getButton(network,R.id.btn_refresh);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefreshListener.refreshTask(v);
            }
        });
    }




    /**
     * 没有数据的时候显示
     *
     * @param nothing
     */
    private TextView tv_noData;
    private ImageView nothingImageView;
    private void initNothingView(View nothing) {
        this.addView( nothing);
        getLinearLayout(nothing,R.id.lay_noData).setVisibility(VISIBLE);
        tv_noData = getTextView(nothing, R.id.tv_noData);
        nothingImageView= getImageView(nothing,R.id.noDataImg);
        setNothingText(nothingText);
        setNothingImg(noDataImg);
    }




    /**
     * 设置没有数据时候的显示的图片
     *
     * @param noDataImg
     */
    private void setNothingImg(Drawable noDataImg) {
        if (noDataImg!=null && nothingImageView != null){
            nothingImageView.setImageDrawable(noDataImg);
        }
    }


    /**
     * 为没数据时候添加提示
     *
     * @param s
     */
    public void setNothingText(String s){
        if(!TextUtils.isEmpty(s)&&tv_noData!=null){
            tv_noData.setText(s);
        }
    }




    /**
     * 正在加载的时候显示
     *
     * @param loading
     */
    private void initLoadingView(View loading) {
        this.addView(loading);
        getLinearLayout(loading,R.id.lay_loading).setVisibility(VISIBLE);
    }



    /**
     * @param view
     * @param viewId
     * @return
     */
    private View findViewById(View view,int viewId){
        return view.findViewById(viewId);
    }



    /**
     *
     * @param view
     * @param id
     * @return
     */
    public TextView getTextView(View view,int id){
        TextView t= (TextView) findViewById(view,id);
        return t;
    }


    /**
     *
     * @param view
     * @param id
     * @return
     */
    public ImageView getImageView(View view,int id){
        ImageView imageView= (ImageView) findViewById(view,id);
        return imageView;
    }



    /**
     *
     * @param view
     * @param id
     * @return
     */
    public LinearLayout getLinearLayout(View view, int id){
        LinearLayout l= (LinearLayout) findViewById(view,id);
        return l;
    }



    /**
     *
     * @param view
     * @param id
     * @return
     */
    public Button getButton(View view,int id){
        Button t= (Button) findViewById(view,id);
        return t;
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_refresh) {
        }
    }


    private OnRefreshListener onRefreshListener;
    public interface OnRefreshListener{
        void refreshTask(View view);
    }


    /**
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}
