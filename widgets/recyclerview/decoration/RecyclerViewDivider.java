package com.meishubao.app.common.widgets.recyclerview.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wangzhe on 2016/11/18.
 * 分割线（在列表项的下面）
 * 在item下面开始绘制
 */

public class RecyclerViewDivider extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST   = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;
    private int      mOrientation;
    private int      mDividerHeight;
    private int      mDividerColor;

    public RecyclerViewDivider(Context context, int orientation) {
        init(context, orientation);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    public RecyclerViewDivider(Context context, int orientation, int height) {
        init(context, orientation);
        mDividerHeight = height;
    }

    public RecyclerViewDivider(Context context, int orientation, int height, int dividerColor) {
        init(context, orientation);
        mDividerHeight = height;
        mDividerColor = dividerColor;
    }

    /**
     * 设置dividerheight
     */
    public void setDividerHeight(int height) {
        mDividerHeight = height;
    }

    /**
     * 设置dividercolor
     */
    public void setDividerColor(int color) {
        mDividerColor = color;
    }

    private void init(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("Invalid orientation.");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    protected void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(child) == (parent.getAdapter().getItemCount() - 1)) {
                continue;
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            if (mDividerColor != 0) {
                mDivider.setColorFilter(mDividerColor, PorterDuff.Mode.ADD);
            }
            mDivider.draw(c);
        }
    }

    protected void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(child) == (parent.getAdapter().getItemCount() - 1)) {
                continue;
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin + Math.round(ViewCompat.getTranslationX(child));
            final int right = left + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            if (mDividerColor != 0) {
                mDivider.setColorFilter(mDividerColor, PorterDuff.Mode.ADD);
            }
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDividerHeight);
        } else {
            outRect.set(0, 0, mDividerHeight, 0);
        }
    }
}