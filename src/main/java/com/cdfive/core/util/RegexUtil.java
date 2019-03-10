package com.cdfive.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexUtil {
    public static String REGEX_PHONE = "^1[3456789][0-9]{9}$";// 手机号
    public static String REGEX_PWD = "^.{6,12}$";// 密码，任意字符6-12位
    public static String REGEX_MAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";// 邮箱
    public static String REGEX_IMG_CODE = "^[0-9a-zA-Z]{4}$";// 图形验证码
    public static String REGEX_SMS_CODE = "^[1-9]{1}[0-9]{5}$";// 短信验证码，100000-999999
    public static String REGEX_SPECIALCHARACTER = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    public static boolean checkPhone(String str) {
        return check(str, REGEX_PHONE);
    }

    public static boolean checkPwd(String str) {
        return check(str, REGEX_PWD);
    }

    public static boolean checkMail(String str) {
        return check(str, REGEX_MAIL);
    }

    public static boolean check(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
