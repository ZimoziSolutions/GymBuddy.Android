package com.zimozi.assessment.util.extension


import androidx.annotation.StringDef
import java.util.regex.Pattern

object ValidationUtil {

    val minPasswordLength = 6
    val EMAIL_ADDRESS_PATTERN =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    val MOBILE_PATTERN = "^[0-9]{6,14}$"


    @StringDef("MM/dd/yyyy", "EEE dd/MM/yy")
    annotation class DatePatterns

}

fun String.isPasswordValid(): Boolean {
    return this.length >= ValidationUtil.minPasswordLength
}

fun String.isEmailValid(): Boolean {
    val pattern = Pattern.compile(ValidationUtil.EMAIL_ADDRESS_PATTERN, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isMobileValid(): Boolean {
    val pattern = Pattern.compile(ValidationUtil.MOBILE_PATTERN, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}
