package com.meishubao.app.common.widgets.classifyview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhe on 2017/1/10.
 * gridview的adapter
 */

public abstract class ClassifyGridViewBaseAdapter<T> extends BaseAdapter {

    private List<T> mDatas = new ArrayList<>();

    private int mIndex = -1;
    private int mGridNum;

    public Context mContext;

    public ClassifyGridViewBaseAdapter(Context context) {
        mContext = context;
    }

    public void addData(List<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public void setIndexGridNum(int index, int gridNum) {
        mIndex = index;
        mGridNum = gridNum;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mIndex == -1 || mDatas.size() == 0) {
            return 0;
        }
        return mDatas.size() > (mIndex + 1) * mGridNum ? mGridNum : (mDatas.size() - mIndex * mGridNum);
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i + mIndex * mGridNum);
    }

    @Override
    public long getItemId(int i) {
        return i + mIndex * mGridNum;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getNewView(i, view, viewGroup, i + mIndex * mGridNum, mDatas);
    }

    public abstract View getNewView(int i, View view, ViewGroup viewGroup, int position, List<T> data);
}
