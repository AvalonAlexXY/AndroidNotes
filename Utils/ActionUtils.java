import java.util.Random;

/**
 * Created by wangzhe on 2016/8/15.
 */
public class ActionUtils {
    private static long lastClickTime;

    /**
     * 防止快速点击
     *
     * @return 是否是快速点击
     */
    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 300) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 生成min--max之间的随机数
     *
     * @param max 最大范围
     * @param min 最小范围
     * @return 随机数
     */
    public static int randomNum(int min, int max) {
        Random random = new Random();
        return random.nextInt(4) % (4) + 1;
    }
}
