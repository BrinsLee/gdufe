package com.brin.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.brin.gdufe.AppConfig;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcUtils {
    /**
     * 求score分数对应的绩点，如90分为4.0，返回字符串"4.0"
     * @param score
     * @return
     */
    public static String calcScore2Gpa(int score) {
        if (score < 60) return "0.0";
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(1.0 + (score - 60) * 0.1);
    }


    /**
     * 将base64编码后的图片进行解码回Bitmap
     * @param str
     * @return
     */
    public static Bitmap base64String2Bitmap(String str) {
        Bitmap bitmap=null;
        try {
            byte[]bitmapArray;
            bitmapArray= Base64.decode(str, Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    //  1-8(周) 1-16(双周)  1,2,4,6,8,10,12(周) 1-9,16(周)
    public static boolean isCurrentWeek(String period, int currentWeek) {
        // 1-8(周) 1-16(双周) 这类
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+)\\((.?)周\\)");
        Matcher matcher = pattern.matcher(period);
        if (matcher.find()) {
            if (!TextUtils.isEmpty(matcher.group(3))) { // 限制单双周
                if ("单".equals(matcher.group(3)) && currentWeek % 2 == 0) {
                    return false;
                }
                if ("双".equals(matcher.group(3)) && currentWeek % 2 != 0) {
                    return false;
                }
            }
            // 不限制单双 或者 限制但符合了
            int begin = Integer.parseInt(matcher.group(1));
            int end = Integer.parseInt(matcher.group(2));
            if (begin <= currentWeek && currentWeek <= end) {
                return true;
            }
            period = matcher.replaceAll("").trim();
            if (period.length() == 0) { // 1-8 找9 不符合，如果不是0说明是 1-9,16(周)这种
                return false;
            }
        }

        // 1,2,4,6,8,10,12(周) 1-9,16(周)
        // 去掉(周)
        pattern = Pattern.compile("\\(.?周\\)");
        matcher = pattern.matcher(period);
        String numberString = period;
        if (matcher.find()) {
            numberString = matcher.replaceAll("");
        }
        // 分割数字
        String[] pps = numberString.trim().split(",");

        if (pps.length == 0) {
            return false;
        }
        for (String pp : pps) {
            // 1-9,16(周) 经过 去掉(周) 和 分割数字后剩下 1-9和16，这里处理1-9
            pattern = Pattern.compile("(\\d+)-(\\d+)");
            matcher = pattern.matcher(pp);
            if (matcher.find()) {
                int begin = Integer.parseInt(matcher.group(1));
                int end = Integer.parseInt(matcher.group(2));
                if (begin <= currentWeek && currentWeek <= end) {
                    return true;
                }
                continue;
            }
            int cur = Integer.parseInt(pp);
            if (currentWeek == cur) {
                return true;
            }
        }
        return false;
    }
}
