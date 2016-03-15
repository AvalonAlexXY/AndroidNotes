import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhe on 2016/3/8.
 */
public class RegexUtils {
    /**
     * 判断是否是手机号
     */
    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
	 * 判断是否含有中文
	 * @param source
	 * @return
	 */
	public static boolean isHaveChinese(String source) {
		String regx = "^[\\x01-\\xff]{1,}";
		return !Pattern.matches(regx, source);
	}
}
