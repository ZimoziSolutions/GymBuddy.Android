package com.zimozi.assessment.util

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateUtils {
    /**
     *MM-dd
     * yyyy-MM
     * HH:mm
     */
    const val FORMAT_MONTH_DAY = "MM-dd"

    /**
     * 年月
     */
    const val FORMAT_YEAR_MONTH = "yyyy-MM"

    /**
     * 年月日
     */
    const val FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd"

    /**
     * 时分
     */
    const val FORMAT_HOUR_MIN = "HH:mm"

    /**
     * 月日时分
     */
    const val FORMAT_MONTH_DAY_HOUR_MIN = "MM-dd HH:mm"
    /**
     * 月日时分秒
     */
    const val FORMAT_MONTH_DAY_HOUR_MIN_SECOND = "MM-dd HH:mm:ss"

    const val FORMAT_YEAR_MONTH_DAY_HOUR_MIN = "yyyy-MM-dd HH:mm"

    const val FORMAT_DAY_MONTH_YEAR_HOUR_MIN = "dd/MM/yyyy HH:mm"

    const val FORMAT_DAY_MONTH_YEAR_HOUR_MIN_HISTORY = "dd/MM/yyyy\nHH:mm"

    const val FORMAT_IOE_END_DATE_TIME = "dd MMMM yyyy, HH:mm:ss"

    const val FORMAT_ASSET_EVENT_DATE_TIME = "dd/MM/yyyy, HH:mm:ss"

    const val FORMAT_ASSET_EVENT_DATE_TIME_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"


    fun long2StringMS(format: String, ms: Long): String {
        return dateToString(format, Date(ms))
    }

    /**
     * 返回：月日时分秒
     * ms 毫秒数
     */
    fun getYearMonthDayHourMinSecondMS(ms: Long): String {
        return long2StringMS(
            FORMAT_YEAR_MONTH_DAY_HOUR_MIN,
            ms
        )
    }

    /**
     * 返回：年月日
     * @param ms 毫秒数
     */
    fun getYearMonthDayMS(ms: Long): String {
        return long2StringMS(
            FORMAT_YEAR_MONTH_DAY,
            ms
        )
    }

    /**
     * @return 月日
     */
    fun getMonthDay(date: Long): String {
        return longToString(
            FORMAT_MONTH_DAY,
            date
        )
    }

    /**
     * @return 时分
     */
    fun getHourMin(date: Long): String {
        return longToString(
            FORMAT_HOUR_MIN,
            date
        )
    }

    /**
     * 返回：年月日
     * @param seconds 秒数
     */
    fun getYearMonthDay(seconds: Long): String {
        return longToString(
            FORMAT_YEAR_MONTH_DAY,
            seconds
        )
    }

    /**
     * 返回：月日时分
     * @param seconds  秒数(ws返回的时间是以秒来计算的)
     */
    fun getYearMonthDayHourMin(seconds: Long): String {
        return longToString(
            FORMAT_MONTH_DAY_HOUR_MIN,
            seconds
        )
    }

    /**
     * 日期转换为字符串
     *
     * @param format 日期格式  比如：yyyyMMdd
     * @param date   日期
     * @return
     */
    fun dateToString(format: String, date: Date): String {
        val mFormat = SimpleDateFormat(format)
        return mFormat.format(date)
    }

    fun longToString(format: String, date: Long): String {
        return dateToString(format, Date(date))
    }

    fun timestampToString(format: String, date: Long): String {
        val mFormat = SimpleDateFormat(format)
        return mFormat.format(date)
    }

    fun getDayMonthYearHourMin(seconds: Long): String {
        return longToString(
            FORMAT_DAY_MONTH_YEAR_HOUR_MIN,
            seconds
        )
    }

    fun getDayMonthYearHourMinHistory(seconds: Long): String {
        return longToString(
            FORMAT_DAY_MONTH_YEAR_HOUR_MIN_HISTORY,
            seconds
        )
    }

    fun getIOEEndDateTime(seconds: Long): String {
        return longToString(
            FORMAT_IOE_END_DATE_TIME,
            seconds
        )
    }

    fun isYesterday(time: Long): Boolean {
        return DateUtils.isToday(time + DateUtils.DAY_IN_MILLIS)
    }

    fun isTomorrow(time: Long): Boolean {
        return DateUtils.isToday(time - DateUtils.DAY_IN_MILLIS)
    }

    fun isToday(time: Long): Boolean = DateUtils.isToday(time)

    fun convertToAssetTime(dateStr: String): String {
        var formattedDate = ""
        val readFormat = SimpleDateFormat(FORMAT_ASSET_EVENT_DATE_TIME_SERVER)
        val writeFormat = SimpleDateFormat(FORMAT_ASSET_EVENT_DATE_TIME)
        var date: Date? = null
        try {
            date = readFormat.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (date != null) {
            formattedDate = writeFormat.format(date)
        }

        return formattedDate
    }
}
