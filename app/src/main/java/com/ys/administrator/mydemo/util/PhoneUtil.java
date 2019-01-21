package com.ys.administrator.mydemo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/12/7.
 * 电话号码工具类
 */

public class PhoneUtil {
    /**
     * 正则表达式 检查字符串是否是手机号
     * @param mobilenumber
     * @return
     */
    public static boolean isMobileNumber(String mobilenumber) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        m = p.matcher(mobilenumber);
        b = m.matches();
        return b;
    }
}
