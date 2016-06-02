package com.benbun.evtmaster.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

/**
 * Created by wangzhe on 2016/6/2.
 */
public class DrawableUtils {

    /**
     * 通过设置tint实现按下时更改图片
     *
     * @param context   context
     * @param imageView 图片
     * @param resId     图片资源id
     * @param colorId1  按下前的颜色
     * @param colorId2  按下后的颜色
     */
    public static void setDrawableColor(Context context, ImageView imageView, int resId, int colorId1, int colorId2) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        int[] colors = new int[]{context.getResources().getColor(colorId1), context.getResources().getColor(colorId2)};
        int[][] states = new int[2][];
        states[0] = new int[]{};
        states[1] = new int[]{android.R.attr.state_pressed};
        ColorStateList colorList = new ColorStateList(states, colors);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(states[0], drawable);//默认效果
        stateListDrawable.addState(states[1], drawable);//按下效果
        Drawable.ConstantState state = stateListDrawable.getConstantState();
        drawable = DrawableCompat.wrap(state == null ? stateListDrawable : state.newDrawable()).mutate();
        DrawableCompat.setTintList(drawable, colorList);
        imageView.setImageDrawable(drawable);
    }


}
