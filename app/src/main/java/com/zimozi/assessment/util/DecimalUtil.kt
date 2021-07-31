package com.zimozi.assessment.util

import android.text.TextUtils
import android.util.Log
import com.zimozi.assessment.util.BigDecimalUtils.showSNormal
import java.math.BigDecimal

/**
 * @Author: Bertking
 * @Date：2019-06-03-11:09
 * @Description: 处理数据格式
 */

class DecimalUtil {

    companion object {
        val TAG = DecimalUtil::class.java.simpleName


        /**
         * 判断字符串是否为有效数字
         * @param str
         */
        fun isNumeric(str: String): Boolean {
            return try {
                BigDecimal(str).toString()
                true
            } catch (e: Exception) {
                false//异常 说明包含非数字。
            }
        }


        /**
         * 精确对比两个数字
         *
         * @param v1 需要被对比的第一个数
         * @param v2 需要被对比的第二个数
         * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
         */
        fun compareTo(v1: String, v2: String): Int {
            return try {
                val b1 = BigDecimal(v1)
                val b2 = BigDecimal(v2)
                b1.compareTo(b2)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                -1
            }

        }


        fun showDepthVolume(value: String): String {
            return if (compareTo(value, "0.0001") <= 0) {
                "0.000"
            } else if (compareTo(value, "1000") >= 0) {
                formatNumber(value)
            } else {
                if (value.contains(".")) {
                    (value + "00000").substring(0, 5)
                } else {
                    "$value.0000".substring(0, 5)
                }
            }
        }

        /**
         * 暂用来解决首页"成交榜"的成交量
         */
        fun formatNumber(str: String, precision: Int = 2): String {
            if (TextUtils.isEmpty(str) || !com.zimozi.assessment.util.BigDecimalUtils.isNumeric(str)) return "--"
            var number = ""
            val b0 = BigDecimal("1000")
            val b1 = BigDecimal("1000000")
            val b2 = BigDecimal("1000000000")
            val temp = BigDecimal(str)

            val value = BigDecimal(str).toPlainString()
            if (temp < b0) {
                Log.d(TAG, "=======number:$value=====")
                return if (value.contains(".") || value == "0") {
                    com.zimozi.assessment.util.BigDecimalUtils.divForDown(str, precision).toPlainString()
                } else {
                    value
                }
            } else if ((temp.compareTo(b0) == 0 || temp.compareTo(b0) == 1) && temp.compareTo(b1) == -1) {
                number = temp.divide(b0, 2, BigDecimal.ROUND_DOWN).toString() + "K"
                return number
            } else if (temp >= b1) {
                number = temp.divide(b1, 2, BigDecimal.ROUND_DOWN).toString() + "M"
                return number
            } else if (temp >= b2) {
                number = temp.divide(b1, 2, BigDecimal.ROUND_DOWN).toString() + "B"
                return number
            } else {
                return showSNormal(number)
            }
        }

        /**
         * 由scale参数指定精度(不四舍五入)
         *
         * 判断isNumber(v1) 是否是合法数值
         * 避免使用isNumeric,因为其会多产生一个BigDecimal对象
         *
         * @param v1    参数
         * @param scale 表示表示需要精确到小数点以后几位。
         */
        fun cutValueByPrecision(v1: String, scale: Int): String {
            var v1 = v1
            if (TextUtils.isEmpty(v1) || v1 == "--" || v1 == "null") {
                v1 = "0"
            }
            return BigDecimal(v1).setScale(scale, BigDecimal.ROUND_DOWN).toPlainString()
        }
    }
}