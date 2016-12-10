import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhe on 2016/3/8.
 */
public class RegexUtils {
	 /**
     * 字符串倒序输出
     *
     * @return 倒序后的字符串
     */
    public static String reverseString(String str) {
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }
/**
     * 将字符串转成Sha1值
     *
     * @param data 需要转换的字符串
     * @return 字符串的Sha1值
     */
    public static String stringToSha1(String data) {
        StringBuilder buf = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for (byte bit : bits) {
                int a = bit;
                if (a < 0)
                    a += 256;
                if (a < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    /**
     * 将字符串转成MD5值
     *
     * @param data 需要转换的字符串
     * @return 字符串的MD5值
     */
    public static String stringToMD5(String data) {
        StringBuilder buf = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for (byte bit : bits) {
                int a = bit;
                if (a < 0)
                    a += 256;
                if (a < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
	/**
     * 提取html中的纯文本
     *
     * @param html 源文件
     * @return 纯文本文件
     */
    public static String textFromHtml(String html) {
        html = html.replaceAll("&nbsp;", "");
        html = html.replaceAll("<[^>]*>", "");
        return html;
    }

    /**
     * 是否是7牛的图片
     *
     * @param url 需要检验的url
     * @return 是否是七牛图片
     */
    public static boolean isQiniuImgUrl(String url) {
        Pattern p = Pattern.compile("(.*)(qiniucdn.com|clouddn.com|s.huodongdashi.com)+(.*)");
        Matcher m = p.matcher(url);
        return m.matches();
    }
    
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
