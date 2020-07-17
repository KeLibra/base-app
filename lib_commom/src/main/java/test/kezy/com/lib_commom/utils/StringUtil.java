package test.kezy.com.lib_commom.utils;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dingzhongsheng
 */
public final class StringUtil {

    /**
     * Don't let anyone instantiate this class
     */
    private StringUtil() {
    }

    public static boolean isNullOrEmpty(String input) {
        if (null == input || input.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否为手机号
     */
    public static boolean isPhoneNumber(String number) {
        if (isNullOrEmpty(number)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^1[3-9][0-9]{9}$");
        return pattern.matcher(number).matches();
    }

    public static String formatePhoneNumber(@NotNull String phone) {
        String newStr = phone.toString();
        StringBuilder result = new StringBuilder();
        newStr = newStr.replaceAll(" ", "");
        int index = 0;
        if (index + 3 < newStr.length()) {
            result.append(newStr.substring(index, index + 3)).append(" ");
            index += 3;
        }
        while (index + 4 < newStr.length()) {
            result.append(newStr.substring(index, index + 4)).append(" ");
            index += 4;
        }
        result.append(newStr.substring(index, newStr.length()));
        return result.toString();
    }


    public static String changePhoneNumber(@NotNull String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
    }

    /**
     * 判断字符串是否为邮箱
     */
    public static boolean isEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        boolean isExist = false;
        Pattern p = Pattern.compile("^[A-Za-z0-9]+([._\\\\-]*[A-Za-z0-9])*@([A-Za-z0-9]+[-A-Z-a-z0-9]*[A-Za-z0-9]+\\.){1,63}[A-Za-z0-9]+$");
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        if (b) {
            isExist = true;
        }
        return isExist;
    }

    public static String subString(String str, int maxlength) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.length() > maxlength) {
            return str.substring(0, maxlength) + "...";
        }
        return str;
    }

    public static String formatNullStr(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    public static boolean isSimplePassword(String numOrStr) {
        return TextUtils.equals(numOrStr, "123456") ? true : isSameNumber(numOrStr);
    }

    public static boolean isSameNumber(String numOrStr) {
        boolean flag = true;
        char str = numOrStr.charAt(0);

        for (int i = 0; i < numOrStr.length(); ++i) {
            if (str != numOrStr.charAt(i)) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    public static boolean isIllegalPassword(String pwdStr) {
        return TextUtils.isEmpty(pwdStr) ? true : (pwdStr.length() >= 6 && pwdStr.length() <= 18 ? pwdStr.matches("^[0-9]+$") || pwdStr.matches("^[A-Za-z]+$") : true);
    }

    public static boolean isPasswordNumberLegal(String pwdStr) {
        return TextUtils.isEmpty(pwdStr) ? true : pwdStr.length() < 6;
    }

    public static boolean isWeakPassword(String pwdStr, String phoneNum) {
        if (!TextUtils.isEmpty(pwdStr) && isPhoneNumber(phoneNum)) {
            int len = phoneNum.length();
            String prefix = phoneNum.substring(0, 8);
            String postfix = phoneNum.substring(len - 8, len);
            return pwdStr.contains(prefix) || pwdStr.contains(postfix);
        } else {
            return true;
        }
    }

    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾
        boolean matches = IDNumber.matches(regularExpression);
        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }
}
