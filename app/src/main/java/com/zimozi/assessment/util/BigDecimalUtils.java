package com.zimozi.assessment.util;


import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalUtils {

    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(String v1, String v2) {
        v1 = v1.replace(",", "");
        v2 = v2.replace(",", "");
        if (isNumeric(v1) && isNumeric(v2)) {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.add(b2);
        } else {
            return new BigDecimal("0.0");
        }
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(String v1, String v2) {
        v1 = v1.replace(",", "");
        v2 = v2.replace(",", "");
        if (isNumeric(v1) && isNumeric(v2)) {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.subtract(b2);
        } else {
            return new BigDecimal("0.0");
        }

    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(String v1, String v2) {
        v1 = v1.replace(",", "");
        v2 = v2.replace(",", "");
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.multiply(b2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new BigDecimal("0.0");
        }
    }


    /**
     * 提供精确的乘法运算。(TODO 舍入)
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(String v1, String v2, int scale) {
        v1 = v1.replace(",", "");
        v2 = v2.replace(",", "");
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_DOWN);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new BigDecimal("0.00");
        }
    }


    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字舍入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, String v2, int scale) {
        v1 = v1.replace(",", "");
        v2 = v2.replace(",", "");
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.divide(b2, scale, BigDecimal.ROUND_DOWN);
        } catch (Exception e) {
            e.printStackTrace();
            return new BigDecimal("0.0");
        }

    }

    /**
     * 此方法不四舍五入
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度。
     *
     * @param v1    参数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal divForDown(String v1, int scale) {
        v1 = v1.replace(",", "");
        if (!isNumeric(v1)) {
            v1 = "0";
        }
        return new BigDecimal(v1).setScale(scale, BigDecimal.ROUND_DOWN);
    }


    /**
     * 此方法四舍五入
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度。
     *
     * @param v1    参数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal divForUp(String v1, int scale) {
        v1 = v1.replace(",", "");
        return new BigDecimal(v1).setScale(scale, BigDecimal.ROUND_UP);
    }


    /**
     * 截取数字
     * 四舍五入
     *
     * @param v1
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return
     */
    public static BigDecimal intercept(String v1, int scale) {
        v1 = v1.replace(",", "");
        if ("NaN".equals(v1)) {
            return new BigDecimal("0").setScale(scale, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(v1).setScale(scale, BigDecimal.ROUND_HALF_UP);
        }
    }


    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(String v, int scale) {
        v = v.replace(",", "");
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        try {
            BigDecimal b = new BigDecimal(v);
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_DOWN);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new BigDecimal("0.0");
        }

    }

    /**
     * 提供精确的类型转换(Float)
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static float convertsToFloat(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    /**
     * 提供精确的类型转换(Int)不进行四舍五入
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static int convertsToInt(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    /**
     * 提供精确的类型转换(Long)
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static long convertsToLong(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }


    /**
     * 返回两个数中大的一个值
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中大的一个值
     */
    public static BigDecimal returnMax(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2);
    }

    /**
     * 返回两个数中小的一个值
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中小的一个值
     */
    public static BigDecimal returnMin(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2);
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(String v1, String v2) {
        v1 = v1.replace(",", "");
        v2 = v2.replace(",", "");
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.compareTo(b2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }

    }


    /**
     * 禁用科学计数法
     *
     * @return 返回double类型
     */
    public static double showDNormal(Double data) {
        return Double.valueOf(showSNormal(data));
    }

    /**
     * 禁用科学计数法
     *
     * @return 返回double类型
     */
    public static String showSNormal(Double data) {
        try {
//            BigDecimal bigDecimal = new BigDecimal(String.valueOf(data));
//            String plainString = NumberFormat.getInstance().format(bigDecimal);
            return showSNormal(String.valueOf(data));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.0";
        }
    }

    /**
     * 禁用科学计数法
     *
     * @return 返回double类型
     */
    public static String showSNormal(BigDecimal data) {
        try {
//            String plainString = NumberFormat.getInstance().format(data);
            return showSNormal(data.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.0";
        }
    }

    public static String showSNormal(String data) {
        return showSNormal(data, 4);
    }

    /**
     * 禁用科学计数法
     * <p>
     * 补充：toPlainString()
     * No scientific notation is used. This methods adds zeros where necessary.
     * return: a string representation of {@code this} without exponent part
     * <p>
     * IAW, 返回的字符串不会带指数形式
     *
     * @return 返回double类型
     */
    public static String showSNormal(String data, int maxFrac) {
        try {
            if (data.contains("\"")) {
                data = stringReplace(data);
            }
            if (data.contains(",")) {
                data = data.replace(",", "");
            }
            BigDecimal bigDecimal = new BigDecimal(data);
            bigDecimal = bigDecimal.setScale(4, BigDecimal.ROUND_DOWN);
            DecimalFormat dfm = new DecimalFormat();
            dfm.setMaximumFractionDigits(maxFrac);
            return dfm.format(bigDecimal);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 去掉双引号
     *
     * @param wifiInfo
     * @return
     */
    public static String stringReplace(String wifiInfo) {
        String str = wifiInfo.replace("\"", "");
        return str;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 判断字符串是否为有效数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }


    public static String showDepthVolume(String value) {
        String temp = new BigDecimal(value).toPlainString();
        if (compareTo(temp, "0.0001") <= 0) {
            return "0.000";
        } else if (compareTo(temp, "1000") >= 0) {
            return formatNumber(temp);
        } else {
            if (temp.contains(".")) {
                return (temp + "00000").substring(0, 5);
            } else {
                String substring = (temp + ".0000").substring(0, 4);
                if (substring.endsWith(".")) {
                    return substring.substring(0, 3);
                } else {
                    return substring;
                }
            }
        }
    }


    public static String formatNumber(String str) {
        Log.d("==111=", "" + str);
        if (TextUtils.isEmpty(str) || !isNumeric(str)) return "--";
        String number = "";
        BigDecimal b0 = new BigDecimal("1000");
        BigDecimal b1 = new BigDecimal("1000000");
        BigDecimal b2 = new BigDecimal("1000000000");
        BigDecimal temp = new BigDecimal(str);
        if (temp.compareTo(b0) == -1) {
            number = str;
            return showSNormal(number);
        } else if ((temp.compareTo(b0) == 0 || temp.compareTo(b0) == 1) && temp.compareTo(b1) == -1) {
            String substring = temp.divide(b0, 2, BigDecimal.ROUND_DOWN).toString().substring(0, 4);
            if (substring.endsWith(".")) {
                number = substring.substring(0, 3);
            } else {
                number = substring;
            }
            return number + "K";
        } else if (temp.compareTo(b1) >= 0) {
            String substring = temp.divide(b1, 2, BigDecimal.ROUND_DOWN).toString().substring(0, 4);
            if (substring.endsWith(".")) {
                number = substring.substring(0, 3);
            } else {
                number = substring;
            }
            return number + "M";
        } else if (temp.compareTo(b2) >= 0) {
            String substring = temp.divide(b2, 2, BigDecimal.ROUND_DOWN).toString().substring(0, 4);
            if (substring.endsWith(".")) {
                number = substring.substring(0, 3);
            } else {
                number = substring;
            }
            return number + "B";
        } else {
            return showSNormal(number);
        }
    }


}
