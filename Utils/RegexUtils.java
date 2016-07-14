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
	 * 判断包换数字和字母
	 */
	public static boolean containsLetterAndNum(String pwd) {
		return containsLetter(pwd) && containsNum(pwd);
	}

	public static boolean isLetterAndNum(String mobile) {
		Pattern p = Pattern.compile("(?!^\\\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{8,}");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}


	/**
	 * 判断包含字母
	 */
	public static boolean containsLetter(String pwd) {
		for (int i = 0; i < pwd.length(); i++) {
			boolean letter = Character.isLetter(pwd.charAt(i));
			if (letter) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断包含数字
	 */
	public static boolean containsNum(String pwd) {
		for (int i = 0; i < pwd.length(); i++) {
			boolean letter = Character.isDigit(pwd.charAt(i));
			if (letter) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是邮箱
	 */
	public static boolean isMail(String mail) {
		Pattern pattern = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
		Matcher m = pattern.matcher(mail);
		return m.matches();
	}

	/**
	 * 过滤特殊字符
	 *
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String stringFilter(String str) throws PatternSyntaxException {
		String regEx = "[/\\:*?<>|\"\n\t]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}
}
