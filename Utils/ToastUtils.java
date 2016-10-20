import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by wangzhe on 2016/10/8.
 * Toast工具类
 */

public class ToastUtils {

    private static Toast mToast = null;
    private static int mToastTime = 500;
    private static int mToastGravity;

    private static void make(final Context context, final String text) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                    if (mToastGravity != 0) {
                        mToast.setGravity(mToastGravity, 0, 0);
                    }

                } else {
                    mToast.setText(text);
                    mToast.setDuration(Toast.LENGTH_LONG);
                    if (mToastGravity != 0) {
                        mToast.setGravity(mToastGravity, 0, 0);
                    }
                }
                mToast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        mToast.cancel();
                    }
                }, mToastTime);
            }
        };
        ((Activity) context).runOnUiThread(runnable);
    }

    /**
     * 默认Toast
     *
     * @param context context
     * @param text    Toast文字
     */
    public static void show(Context context, String text) {
        make(context, text);
    }

    /**
     * 自定义时间Toast
     *
     * @param context context
     * @param text    Toast文字
     * @param time    Toast时间
     */
    public static void showWithDuration(Context context, String text, long time) {
        mToastTime = (int) time;
        make(context, text);
    }

    /**
     * 自定义位置Toast
     *
     * @param context context
     * @param text    Toast文字
     * @param grativy Toast位置
     */
    public static void showWithGravity(Context context, String text, int grativy) {
        mToastGravity = grativy;
        make(context, text);
    }

    /**
     * 自定义位置和时间Toast
     *
     * @param context context
     * @param text    Toast文字
     * @param time    Toast时间
     * @param grativy Toast位置
     */
    public static void show(Context context, String text, long time, int grativy) {
        mToastGravity = grativy;
        mToastTime = (int) time;
        make(context, text);
    }
}
