package com.benbun.evtmaster.common.view.banner;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benbun.evtmaster.common.bean.BannerBean;
import com.benbun.evtmaster.utils.ImageUtils;
import com.benbun.evtmaster.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class BannerView extends LinearLayout implements ViewPager.OnPageChangeListener {

    private Context mContext;
    private List<BannerBean> mData;

    private FrameLayout mRootView;
    private ViewPager mViewPager;
    private LinearLayout mBottom;

    private ArrayList<View> views = new ArrayList<>();
    private ArrayList<ImageView> mButtomViews = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();

    private int selectPoint;
    private int unselectPoint;

    private int SCROLL_TIME = 3000;
    private Handler mHandler = null;
    private AutoRollRunnable mAutoRollRunnable = null;
    private int current;//当前选中的item

    private OnBannerClickListener mListener;
    private TextView mTitle;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mRootView = new FrameLayout(mContext);
        //viewpager
        mViewPager = new ViewPager(mContext);
        mViewPager.addOnPageChangeListener(this);
        mRootView.addView(mViewPager);

        //下方标题
        mTitle = new TextView(mContext);
        mTitle.setBackgroundColor(Color.argb(76, 0, 0, 0));
        FrameLayout.LayoutParams tv_params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.dip2px(mContext, 40));
        tv_params.gravity = Gravity.BOTTOM;
        mTitle.setGravity(Gravity.CENTER_VERTICAL);
        mTitle.setPadding(UiUtils.dip2px(mContext, 12), 0, 0, 0);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(16);
        mTitle.setMaxWidth(UiUtils.dip2px(mContext, 280));
        mTitle.setLayoutParams(tv_params);
        mRootView.addView(mTitle);

        //指示点
        mBottom = new LinearLayout(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        params.setMargins(0, 0, UiUtils.dip2px(mContext, 16), UiUtils.dip2px(mContext, 8));
        mRootView.addView(mBottom, params);

        mAutoRollRunnable = new AutoRollRunnable();
        mHandler = new Handler();
        addView(mRootView);
    }


    public void setData(List<BannerBean> data) {
        mData = data;
        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < data.size(); i++) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(mParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageUtils.loadImg(mContext, data.get(i).getImg(), iv, true);
            views.add(iv);
            final int finalI = i;
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.click(finalI);
                    }
                }
            });
            mTitles.add(data.get(i).getTitle());
        }
        BannerAdapter mAdapter = new BannerAdapter(views);
        mViewPager.setAdapter(mAdapter);
    }

    public void scrollTime(int time) {
        SCROLL_TIME = time;
    }


    public void setSelectPoint(int select) {
        selectPoint = select;
    }


    public void setUnSelectPoint(int unselect) {
        unselectPoint = unselect;
    }

    public void start() {
        int margin = UiUtils.dip2px(mContext, 1);
        for (int i = 0; i < mData.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiUtils.dip2px(mContext, 8), UiUtils.dip2px(mContext, 8));
            params.setMargins(margin, 0, margin, 0);
            imageView.setLayoutParams(params);
            imageView.setImageResource(unselectPoint);
            mButtomViews.add(imageView);
            mBottom.addView(imageView);
        }
        mButtomViews.get(0).setImageResource(selectPoint);
        mTitle.setText(mTitles.get(0));
        startScroll();
    }

    public void setBannerClickListener(OnBannerClickListener listener) {
        mListener = listener;
    }

    private void startScroll() {
        mAutoRollRunnable.start();
    }

    private void stopRoll() {
        mAutoRollRunnable.stop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mAutoRollRunnable.stop();
        mAutoRollRunnable.start();
        mTitle.setText(mTitles.get(position));
        for (int i = 0; i < mData.size(); i++) {
            if (i == position) {
                mButtomViews.get(i).setImageResource(selectPoint);
            } else {
                mButtomViews.get(i).setImageResource(unselectPoint);
            }
        }
        if (position == mData.size() - 1) {
            current = -1;
        } else {
            current = position;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class AutoRollRunnable implements Runnable {

        //是否在轮播的标志
        boolean isRunning = false;

        public void start() {
            if (!isRunning) {
                isRunning = true;
                mHandler.removeCallbacks(this);
                mHandler.postDelayed(this, SCROLL_TIME);
            }
        }

        public void stop() {
            if (isRunning) {
                mHandler.removeCallbacks(this);
                isRunning = false;
            }
        }

        @Override
        public void run() {
            if (isRunning) {
                mViewPager.setCurrentItem(current + 1);
                mHandler.postDelayed(this, SCROLL_TIME);
            }
        }
    }


    public interface OnBannerClickListener {
        void click(int positon);
    }

    /**
     * adapter
     */
    public class BannerAdapter extends PagerAdapter {

        //界面列表
        private ArrayList<View> views;

        public BannerAdapter(ArrayList<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views != null ? views.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);
            return views.get(position);
        }

    }
}
