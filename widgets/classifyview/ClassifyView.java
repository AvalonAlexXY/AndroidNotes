package com.meishubao.app.common.widgets.classifyview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.meishubao.app.R;
import com.meishubao.app.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhe on 2017/1/10.
 * 分类使用的view，使用viewpager+gridview，可左右滑动
 */

public class ClassifyView<T> extends FrameLayout {

    private static final String TAG = "ClassifyView";

    private Context mContext;

    private XRefreshView mRefresh;
    private ViewPager    mViewPager;
    private LinearLayout mBottom;

    private List<T> mDatas;
    private int     mGridNum;
    private int     mNumColumns;//gridview的列数
    private int     mCurIndex;

    private List<View>           mViewLists   = new ArrayList<>();
    private ArrayList<ImageView> mButtomViews = new ArrayList<>();

    public ClassifyView(Context context) {
        this(context, null);
    }

    public ClassifyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        LinearLayout mRootView = new LinearLayout(mContext);
        mRootView.setOrientation(LinearLayout.VERTICAL);

        mViewPager = new ViewPager(mContext);
        mRootView.addView(mViewPager, LayoutParams.MATCH_PARENT, UiUtils.dip2px(mContext, 150));

        //指示点
        mBottom = new LinearLayout(mContext);
        mBottom.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams mPointParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.dip2px(mContext, 30));
        mBottom.setLayoutParams(mPointParams);
        mBottom.setGravity(Gravity.CENTER);
        mRootView.addView(mBottom);

        addView(mRootView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mButtomViews.get(mCurIndex).setImageResource(R.drawable.org_banner_unselect);
                mButtomViews.get(position).setImageResource(R.drawable.org_banner_select);
                mCurIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 如果嵌套xrefresh时需要处理焦点问题，需要传入xrefresh
     */
    public void setXRefresh(XRefreshView xrefresh) {
        mRefresh = xrefresh;
    }

    /**
     * 设置数据
     */
    public void setDatas(List<T> datas, int gridNum, int numColumns) {
        mDatas = datas;
        mGridNum = gridNum;
        mNumColumns = numColumns;
    }

    /**
     * 设置gridview的adapter
     */
    public void setAdapter(OnClassifyAdapterListener listener) {
        int pageNum = (int) Math.ceil(mDatas.size() * 1.0 / mGridNum);
        //指示点
        int margin = UiUtils.dip2px(mContext, 2);
        for (int i = 0; i < pageNum; i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiUtils.dip2px(mContext, 4), UiUtils.dip2px(mContext, 4));
            params.gravity = Gravity.CENTER;
            params.setMargins(margin, 0, margin, 0);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setImageResource(R.drawable.org_banner_select);
            } else {
                imageView.setImageResource(R.drawable.org_banner_unselect);
            }
            mBottom.addView(imageView);
            mButtomViews.add(imageView);
        }
        //viewpager
        for (int i = 0; i < pageNum; i++) {
            GridView gridView = new GridView(mContext);
            gridView.setNumColumns(mNumColumns);
            mViewLists.add(gridView);
            ClassifyGridViewBaseAdapter adapter = listener.setAdapter(gridView);
            gridView.setOnItemClickListener((adapterView, view, i1, l) -> listener.onClickPosition((int) adapter.getItemId(i1)));
            adapter.addData(mDatas);
            adapter.setIndexGridNum(i, mGridNum);
            gridView.setAdapter(adapter);
        }
        mViewPager.setAdapter(new ViewPagerAdapter(mViewLists));
    }

    public interface OnClassifyAdapterListener {
        ClassifyGridViewBaseAdapter setAdapter(GridView gridView);

        void onClickPosition(int position);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (mRefresh != null) {
                    mRefresh.disallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mRefresh != null) {
                    mRefresh.disallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                mRefresh.disallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (mRefresh != null) {
                    mRefresh.disallowInterceptTouchEvent(false);
                }
                break;
            default:
                mRefresh.disallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
