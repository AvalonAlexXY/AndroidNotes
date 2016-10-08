package com.benbun.evtmaster.common.method;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import java.net.URL;

/**
 * Created by wangzhe on 2016/8/11.
 * 格式化HTMl，使TextView正常显示<img></img>标签
 */
public class FormatHtmlWithImg {

    private static Handler handler = new Handler();

    public static void format(final String html, final TextView textView){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Spanned text = Html.fromHtml(html, imgGetter, null);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(text);
                    }
                });
            }
        }).start();
    }

    private static Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            try {
                drawable = Drawable.createFromStream(
                        new URL(source).openStream(), "");
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                        .getIntrinsicHeight());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return drawable;
        }
    };

}
