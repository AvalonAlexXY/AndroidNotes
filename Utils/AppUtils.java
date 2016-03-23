
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * App相关工具类
 * Created by zhe on 2016/3/23.
 */
public class AppUtils {
    /**
     * 判断app是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
