package com.distributiongoods.jbh.melibrary.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.distributiongoods.jbh.melibrary.R;

import java.lang.reflect.Method;


/**
 * Created by zbsdata on 2018/2/27.
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {

    /**
     * 标题
     */
    private String titleText = null;
    /**
     * 标题颜色
     */
    private int titleColor = Color.WHITE;
    /**
     * 返回按钮Icon
     */
    private int backIcon = R.drawable.back;
    /**
     * 是否显示返回按钮
     */
    private boolean isBackVisible;
    /**
     * 是否显示右按钮
     */
    private boolean isRightVisible;
    /**
     * 有按钮内容
     */
    private String rightText = null;
    /**
     * 右边按钮的文字颜色
     */
    private int rightTextColor = Color.WHITE;
    /**
     * 右边按钮的文字大小
     */
    private int rightTextSize;
    private int backGroundColor;
    private boolean isDetail;
    private boolean isVisibilityIocn=false;
    private ImageView icon_;

    /**
     * 返回按键
     */
    private ImageView goBack;
    /**
     * 标题
     */
    private TextView tvTitle;
    /**
     * 右按钮
     */
    private TextView btn_right;
    private View view;


    private FrameLayout lay_cart;
    private TextView tv_num;
    private int cartNum=0;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        addChild(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TitleBar_title) {
                titleText = a.getString(attr);

            } else if (attr == R.styleable.TitleBar_titleColor) {
                titleColor = a.getColor(attr, titleColor);

            } else if (attr == R.styleable.TitleBar_backIcon) {
                backIcon = a.getResourceId(attr, R.drawable.back);

            } else if (attr == R.styleable.TitleBar_isBackVisible) {
                isBackVisible = a.getBoolean(attr, false);

            } else if (attr == R.styleable.TitleBar_isRightVisible) {
                isRightVisible = a.getBoolean(attr, false);

            } else if (attr == R.styleable.TitleBar_rightText) {
                rightText = a.getString(attr);

            } else if (attr == R.styleable.TitleBar_rightTextColor) {
                rightTextColor = a.getColor(attr, rightTextColor);

            } else if (attr == R.styleable.TitleBar_rightTextSize) {
                rightTextSize = a.getDimensionPixelSize(attr, 16);

            } else if (attr == R.styleable.TitleBar_isDetail) {
                isDetail = a.getBoolean(attr, false);

            } else if (attr == R.styleable.TitleBar_isVisibilityIocn) {
                isVisibilityIocn = a.getBoolean(attr, false);

            }
        }
        a.recycle();
        initView(context, view);
    }

    /**
     * 添加子控件
     *
     * @param context
     */
    private void addChild(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.layout_bar, null);
        addView(view);
    }


    public void setBackVisible(boolean backVisible) {
        isBackVisible = backVisible;
        if (isBackVisible) {
            goBack.setVisibility(VISIBLE);
        } else {
            goBack.setVisibility(GONE);
        }
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
        view.setBackgroundColor(backGroundColor);
    }

    /**
     * @param view
     */
    private void initView(Context context, View view) {
        TextView top = view.findViewById(R.id.top);
        stateBarTask((Activity) context, top);
        icon_=view.findViewById(R.id.icon_);
        if(isVisibilityIocn){
            icon_.setVisibility(VISIBLE);
        }else {
            icon_.setVisibility(GONE);
        }
        goBack = view.findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
        goBack.setImageResource(backIcon);
        if (isBackVisible) {
            goBack.setVisibility(VISIBLE);
        } else {
            goBack.setVisibility(GONE);
        }
        tvTitle = view.findViewById(R.id.title);
        tvTitle.setTextColor(titleColor);
        tvTitle.setText(titleText);

        btn_right = view.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);
        btn_right.setText(rightText);
        if (isRightVisible) {
            btn_right.setVisibility(VISIBLE);
        } else {
            btn_right.setVisibility(GONE);
        }
        btn_right.setTextColor(rightTextColor);
//        btn_right.setTextSize(rightTextSize);


        lay_cart = view.findViewById(R.id.lay_cart);
        lay_cart.setOnClickListener(this);
        if (isDetail) {
            lay_cart.setVisibility(VISIBLE);
        } else {
            lay_cart.setVisibility(GONE);
        }
        tv_num=view.findViewById(R.id.tv_num);
        if(cartNum==0){
            tv_num.setVisibility(GONE);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.goBack) {
            if (listener != null)
                listener.onClick(v);
        } else if (i == R.id.btn_right) {
            if (rightListener != null)
                rightListener.onClick(v);
        } else if (i == R.id.lay_cart) {
            if (toCartListener != null)
                toCartListener.onClick(v);
        }
    }


    /**
     * @param titleText
     */
    public void setTitleText(String titleText) {
        this.titleText = titleText;
        tvTitle.setText(titleText);
    }


    /**
     * @param titleColor
     */
    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        tvTitle.setTextColor(titleColor);
    }


    /**
     * @param backIcon
     */
    public void setBackIcon(int backIcon) {
        this.backIcon = backIcon;
        goBack.setImageResource(backIcon);
    }


    public void setRightText(String rightText) {
        this.rightText = rightText;
        btn_right.setText(rightText);
    }


    /**
     * @param rightTextColor
     */
    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        btn_right.setTextColor(rightTextColor);
    }


    /**
     * @param cartNum
     */
    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
        if(cartNum>0){
            tv_num.setVisibility(VISIBLE);
            tv_num.setText(String.valueOf(cartNum));
        }
    }

    private OnClickListener listener;

    public void setBackListener(OnClickListener listener) {
        this.listener = listener;
    }


    private OnClickListener rightListener;

    public void setRightListener(OnClickListener rightListener) {
        this.rightListener = rightListener;
    }

    private OnClickListener toCartListener;
    public void setToCartListener(OnClickListener toCartListener) {
        this.toCartListener = toCartListener;
    }


    /**
     *
     * @param context
     */
    public static void stateBarTask(Activity context,View view){
        if (Build.VERSION.SDK_INT >=19 && Build.VERSION.SDK_INT < 21) {
            //设置透明状态栏  titleBar
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //设置透明导航栏  navigationBar
//            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            viewStatueHeight.setVisibility(View.VISIBLE);
//            viewStatueHeight.setMinimumHeight(Utils.getStatusBarHeight(this));
        }


        if (Build.VERSION.SDK_INT >= 21) {
            View decorView =context. getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
//                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|//暗色
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(option);
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
            if(!checkDeviceHasNavigationBar(context)){
                context.getWindow().setNavigationBarColor(Color.WHITE);
            }
        }


        /**设置状态栏的高度*/
        setStatusBarHeight(context,view);
    }




    /**
     * 检测navigationbar 是否可用
     *
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }


    /**
     * @param context
     * @param view
     */
    public static void setStatusBarHeight(Context context, View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            view.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height=getStatusBarHeight(context);
            view.setLayoutParams(params);
        }else {
            view.setVisibility(View.GONE);
        }
    }



    /**
     * 获得状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT>=19) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }
        return result;
    }

}
