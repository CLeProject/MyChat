package com.le.mychat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.le.mychat.R;
import com.le.mychat.utils.SizeUtil;


/**
 * 自定义ActionBar
 *
 * @author wangcccong
 * @version 1.1406 create at： Mon, 28 Apr. 2014 update at: Thur, 28 Aug. 2014
 */
public class ActionBarCompat extends LinearLayout {

    private final static String TAG = "ActionBarCompat";

    private Context mContext;
    private RelativeLayout mTotalLayout;
    // 左方布局界面
    private LinearLayout mLeftView;
    private ImageView leftIco;

    // 中间布局
    private LinearLayout mTitleLinear;
    private LinearLayout historyLinear;
    private TextView mTitle;
//    private TextView history;
    // private ProgressBar mProgressBar;

    // 右方布局
    private LinearLayout mRightView;
    private ImageView rightIco;

    private View mRightCustView;
    private View mLeftCustView;

    public ActionBarCompat(Context context) {
        super(context);
        init(context, null);
    }

    public ActionBarCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Log.i(TAG, "init--ActionBarCompat");
        this.mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        mTotalLayout = new RelativeLayout(context);
        addView(mTotalLayout, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mTotalLayout.setGravity(Gravity.CENTER_VERTICAL);

        // 中间布局(ProgressBar + TextView)
        mTitleLinear = new LinearLayout(context);
        RelativeLayout.LayoutParams titlePP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        // titlePP.setMargins(80, 0, 80, 0);
        titlePP.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mTitleLinear.setLayoutParams(titlePP);
        mTitleLinear.setOrientation(HORIZONTAL);
        mTitleLinear.setGravity(Gravity.CENTER);

        mTitle = new TextView(context);
        mTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mTitle.setFocusableInTouchMode(true);
        mTitle.setMarqueeRepeatLimit(-1);
        mTitle.setEllipsize(TruncateAt.MARQUEE);
        mTitle.setSingleLine(true);
        mTitle.setHorizontallyScrolling(true);
        mTitleLinear.addView(mTitle);
        mTotalLayout.addView(mTitleLinear);

        // mProgressBar = new ProgressBar(context, null,
        // android.R.attr.progressBarStyleSmallTitle);
        // LayoutParams lpp = new LayoutParams(SizeUtil.dp2px(context, 16),
        // SizeUtil.dp2px(context, 16));
        // lpp.setMargins(0, 0, 5, 0);
        // mProgressBar.setLayoutParams(lpp);
        // mTitleLinear.addView(mProgressBar);
        // mProgressBar.setVisibility(View.INVISIBLE);

        // 左方按钮
        mLeftView = new LinearLayout(context);
        mLeftView.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams leftPP = new RelativeLayout.LayoutParams(-2, -1);
        leftPP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftPP.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        // add ico area
        mLeftView.setLayoutParams(leftPP);
        mLeftView.setPadding(25, 0, 25, 0);
        mTotalLayout.addView(mLeftView);
        leftIco = new ImageView(mContext);
        int size = SizeUtil.dp2px(mContext, 25);
        LayoutParams lp = new LayoutParams(size, size);
        lp.gravity = Gravity.CENTER;
        leftIco.setLayoutParams(lp);
        mLeftView.addView(leftIco);
        Rect r = new Rect(0, 0, 50 + size, SizeUtil.dp2px(mContext, 50));
        mLeftView.setTouchDelegate(new TouchDelegate(r, leftIco));

        // 右方按钮
        mRightView = new LinearLayout(context);
        mRightView.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams rightPP = new RelativeLayout.LayoutParams(-2, -1);
        rightPP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightPP.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        mTotalLayout.addView(mRightView);
        mRightView.setLayoutParams(rightPP);
        mRightView.setVisibility(View.INVISIBLE);

        // add ico area
        rightIco = new ImageView(mContext);
        rightIco.setLayoutParams(lp);
        mRightView.setPadding(25, 0, 25, 0);
        mRightView.addView(rightIco);
        Rect r2 = new Rect(0, 0, size + 50, SizeUtil.dp2px(mContext, 50));
        mRightView.setTouchDelegate(new TouchDelegate(r2, rightIco));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * 设置资源id(可以是布局文件，也可以是drawable资源文件)
     *
     * @param id
     */
    public void setLeftViewResourceId(int id) {
        mLeftView.setVisibility(View.VISIBLE);
        leftIco.setBackgroundResource(id);
    }

    /**
     * 设置左方监听
     *
     * @param listener {@link OnClickListener}
     */
    public void setLeftViewAction(OnClickListener listener) {
        leftIco.setOnClickListener(listener);
    }

    /**
     * 设置左方资源和监听
     *
     * @param listener {@link OnClickListener}
     */
    public void setLeftViewResAction(int id, OnClickListener listener) {
        setLeftViewResourceId(id);
        setLeftViewAction(listener);
    }

    public void setLeftViewAction(View view, OnClickListener listener) {
        leftIco.setVisibility(View.GONE);
        if (mLeftCustView == view) {
            mLeftCustView.setVisibility(View.VISIBLE);
            return;
        }
        mLeftCustView = view;
        mLeftCustView.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams lPP = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lPP.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        lPP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lPP.setMargins(SizeUtil.dp2px(getContext(), 14), 0, 0, 0);
        mTotalLayout.addView(view);
        mLeftCustView.setLayoutParams(lPP);
        mLeftCustView.setOnClickListener(listener);
    }


    /**
     * 隐藏左方控件
     */
    public void hideLeftView() {
        mLeftView.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示左方控件，默认显示
     */
    public void showLeftView() {
        mLeftView.setVisibility(View.VISIBLE);
    }

    /**
     * 设置progressbar的样式
     *
     * @param drawable
     */
    public void setProgressDrawable(Drawable drawable) {
        // mProgressBar.setIndeterminateDrawable(drawable);
    }

    /**
     * 显示加载的progressbar
     */
    public void showProgressBar() {
        // mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏progressBar
     */
    public void hideProgressBar() {
        // mProgressBar.setVisibility(View.INVISIBLE);
    }

//    public void setHisTx(String his) {
//        history.setText(his);
//    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setTitle(int resId) {
        mTitle.setVisibility(View.GONE);
        View.inflate(mContext, resId, mTitleLinear);
    }

    /**
     * @param size
     */
    public void setTitleSize(float size) {
        mTitle.setTextSize(size);
    }

    /**
     * @param color
     */
    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    /**
     * @param colors {@link ColorStateList}
     */
    public void setTitleColor(ColorStateList colors) {
        mTitle.setTextColor(colors);
    }

    public void addRightViewAction(View view, OnClickListener listener) {
        if (mRightCustView == view) {
            mRightCustView.setVisibility(View.VISIBLE);
            return;
        }
        mRightCustView = view;
        mRightCustView.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams rightPP = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightPP.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        rightPP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightPP.setMargins(0, 0, SizeUtil.dp2px(getContext(), 14), 0);
        mTotalLayout.addView(view);
        mRightCustView.setLayoutParams(rightPP);
        mRightCustView.setOnClickListener(listener);
    }

    /**
     * 添加右方控件,并设置监听（此方法只能添加Drawable ID，适用于只在右方添加一个控件）
     *
     * @param id
     * @param listener
     */
    public void addRightResAction(int id, OnClickListener listener) {
        if (mRightCustView != null)
            mRightCustView.setVisibility(View.GONE);

        mRightView.setVisibility(View.VISIBLE);
        rightIco.setBackgroundResource(id);
        rightIco.setOnClickListener(listener);
    }

    public void setRightView(int id) {
        mRightView.setVisibility(View.VISIBLE);
        View.inflate(mContext, id, mRightView);

    }

    /**
     * 隐藏右方控件
     */
    public void hideRightView() {
        if (mRightView != null)
            mRightView.setVisibility(View.INVISIBLE);
        if (mRightCustView != null)
            mRightCustView.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示右方控件
     */
    public void showRightView() {
        if (mRightView != null)
            mRightView.setVisibility(View.VISIBLE);
        if (mRightCustView != null)
            mRightCustView.setVisibility(View.VISIBLE);
    }
}
