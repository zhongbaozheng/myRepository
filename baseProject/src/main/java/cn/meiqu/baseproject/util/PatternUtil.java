package cn.meiqu.baseproject.util;

import java.util.regex.Pattern;

public class PatternUtil {
	//private static String re_phone = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    private static String re_phone = "^[1][3-8]+\\d{9}$";
	private static String re_username = "\\d{5,20}";
	private static String re_password = "^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,18}$";
	private static String re_email = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

	public static boolean isPhone(String input){
		return Pattern.matches(re_phone, input);
	}

	public static boolean isUsername(String input) {
		// TODO Auto-generated method stub
		return Pattern.matches(re_username, input);
	}

	public static boolean isPassword(String input) {
		// TODO Auto-generated method stub
		return Pattern.matches(re_password, input);
	}

    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        return Pattern.matches(re_email, email);
    }
}
