package com.meishubao.app.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.meishubao.app.R;

/**
 * Created by wangzhe on 2016/11/18.
 * 图片工具类
 */

public class ImageUtils {

    /**
     * Context
     */


    public static <T> void loadImg(Context context, T imgUrl, ImageView imageView) {
        Glide.with(context)
                .load(imgUrl)
                .into(imageView);
    }

    private static <T> void loadImgWithPlaceHolder(Context context, T imgPath, ImageView imageView) {
        Glide.with(context)
                .load(imgPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static <T> void loadImg(Context context, T imgPath, ImageView imageView, boolean isPlaceHolder) {
        if (isPlaceHolder) {
            loadImgWithPlaceHolder(context, imgPath, imageView);
        } else {
            loadImg(context, imgPath, imageView);
        }
    }

    /**
     * Activity
     */


    public static <T> void loadImg(Activity context, T imgUrl, ImageView imageView) {
        Glide.with(context)
                .load(imgUrl)
                .into(imageView);
    }

    private static <T> void loadImgWithPlaceHolder(Activity context, T imgPath, ImageView imageView) {
        Glide.with(context)
                .load(imgPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static <T> void loadImg(Activity context, T imgPath, ImageView imageView, boolean isPlaceHolder) {
        if (isPlaceHolder) {
            loadImgWithPlaceHolder(context, imgPath, imageView);
        } else {
            loadImg(context, imgPath, imageView);
        }
    }


    /**
     * Fragment
     */


    public static <T> void loadImg(Fragment context, T imgUrl, ImageView imageView) {
        Glide.with(context)
                .load(imgUrl)
                .into(imageView);
    }

    private static <T> void loadImgWithPlaceHolder(Fragment context, T imgPath, ImageView imageView) {
        Glide.with(context)
                .load(imgPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static <T> void loadImg(Fragment context, T imgPath, ImageView imageView, boolean isPlaceHolder) {
        if (isPlaceHolder) {
            loadImgWithPlaceHolder(context, imgPath, imageView);
        } else {
            loadImg(context, imgPath, imageView);
        }
    }
}
