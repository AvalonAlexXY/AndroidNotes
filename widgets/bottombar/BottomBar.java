package com.benbun.evtmaster.common.view.bottombar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benbun.evtmaster.R;
import com.benbun.evtmaster.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class BottomBar extends LinearLayout {

    private Context mContext;
    private LinearLayout mRootView;
    /**
     * item的个数
     */
    private int ITEM_SIZE;
    /**
     * 选中状态和未选中状态的图标和title颜色
     */
    private List<Integer> mNormalIcon = new ArrayList<>();
    private List<Integer> mSelectIcon = new ArrayList<>();
    private int mNormalColor;
    private int mSelectColor;

    /**
     * 需要改变的view
     */
    private List<ImageView> mIcon = new ArrayList<>();
    private List<ImageView> mMsg = new ArrayList<>();
    private List<TextView> mText = new ArrayList<>();

    private OnBottomBarSelectListener mListener;


    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mRootView = new LinearLayout(mContext);
        mRootView.setOrientation(HORIZONTAL);

        addView(mRootView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 设置item的个数，在这个方法里，初始化item
     *
     * @param size item个数
     */
    public void setItemSize(int size) {
        ITEM_SIZE = size;
        for (int i = 0; i < size; i++) {
            RelativeLayout mItemView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.bottombar_item, null);
            RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(
                    UiUtils.getScreenWidth(mContext) / size, ViewGroup.LayoutParams.MATCH_PARENT);
            mItemView.setLayoutParams(itemParams);

            ImageView iv = (ImageView) mItemView.findViewById(R.id.bottom_img);
            TextView tv = (TextView) mItemView.findViewById(R.id.bottom_title);
            ImageView msg = (ImageView) mItemView.findViewById(R.id.bottom_msg);

            mIcon.add(i, iv);
            mText.add(i, tv);
            mMsg.add(i, msg);

            mItemView.setOnClickListener(new BottomClick(i));

            mRootView.addView(mItemView);
        }
    }

    /**
     * 设置底部文字
     *
     * @param title 文字
     */
    public void setTitle(List<String> title) {
        for (int i = 0; i < ITEM_SIZE; i++) {
            mText.get(i).setText(title.get(i));
        }
    }

    /**
     * 设置未选中装填的icon
     *
     * @param icon icon
     */
    public void setNormalIconAndColor(List<Integer> icon, int color) {
        mNormalIcon = icon;
        mNormalColor = color;
        for (int i = 0; i < ITEM_SIZE; i++) {
            mIcon.get(i).setImageResource(icon.get(i));
            mText.get(i).setTextColor(mContext.getResources().getColor(color));
        }
    }

    /**
     * 设置选中状态的icon
     *
     * @param icon icon
     */
    public void setSelectIconAndColor(List<Integer> icon, int color) {
        mSelectIcon = icon;
        mSelectColor = color;
        setSelect(0);
    }

    /**
     * 选中状态的item
     *
     * @param position item的位置
     */
    public void setSelect(int position) {
        for (int i = 0; i < ITEM_SIZE; i++) {
            if (position == i) {
                mIcon.get(i).setImageResource(mSelectIcon.get(i));
                mText.get(i).setTextColor(mContext.getResources().getColor(mSelectColor));
            } else {
                mIcon.get(i).setImageResource(mNormalIcon.get(i));
                mText.get(i).setTextColor(mContext.getResources().getColor(mNormalColor));
            }
        }
    }

    /**
     * 显示消息提示
     *
     * @param positon 消息提示的位置
     */
    public void showMsg(int positon) {
        mMsg.get(positon).setVisibility(VISIBLE);
    }

    /**
     * 隐藏消息提示
     *
     * @param positon 消息提示的位置
     */
    public void hideMsg(int positon) {
        mMsg.get(positon).setVisibility(GONE);
    }


    /**
     * 点击item的监听，点击同时改变状态，回调点击的位置
     */
    private class BottomClick implements OnClickListener {

        private int mPosition;

        public BottomClick(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            for (int i = 0; i < ITEM_SIZE; i++) {
                if (mPosition == i) {
                    mIcon.get(i).setImageResource(mSelectIcon.get(i));
                    mText.get(i).setTextColor(mContext.getResources().getColor(mSelectColor));
                } else {
                    mIcon.get(i).setImageResource(mNormalIcon.get(i));
                    mText.get(i).setTextColor(mContext.getResources().getColor(mNormalColor));
                }
            }
            if (mListener != null) {
                mListener.bottomBarSelect(mPosition);
            }
        }
    }

    public void setOnBottomBarSelectListener(OnBottomBarSelectListener listener) {
        mListener = listener;
    }

    public interface OnBottomBarSelectListener {
        void bottomBarSelect(int position);
    }

}
