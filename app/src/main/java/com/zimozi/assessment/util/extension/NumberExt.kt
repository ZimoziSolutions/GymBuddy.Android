package com.zimozi.assessment.util.extension

import com.zimozi.assessment.util.BigDecimalUtils.isNumeric
import java.math.BigDecimal

inline fun String?.toNumber(): BigDecimal {
    if (this == null) {
        return BigDecimal(0)
    }
    return if (isNumeric(this)) {
        BigDecimal(this)
    } else {
        BigDecimal(0)
    }
}

//inline fun String?.showFormatNumber(maxFrac: Int = 4, isShowSymbol: Boolean? = false): String {
//    if (this == null) {
//        return "0"
//    }
//
//    return (if (isShowSymbol == true) PublicInfoManager.currencySymbol else "") + BigDecimalUtils.showSNormal(
//        this,
//        maxFrac
//    )
//}

inline fun Double?.showFormatNumber(): String {
    if (this == null) {
        return "0"
    }
    return com.zimozi.assessment.util.BigDecimalUtils.showSNormal(this)
}

//inline fun Double?.showFormatNumber(maxFrac: Int = 4, isShowSymbol: Boolean? = false): String {
//    if (this == null) {
//        return "0"
//    }
//    return (if (isShowSymbol == true) PublicInfoManager.currencySymbol else "") + BigDecimalUtils.showSNormal(
//        this.toString(),
//        maxFrac
//    )
//}

inline fun BigDecimal?.showFormatNumber(): String {
    if (this == null) {
        return "0"
    }
    return com.zimozi.assessment.util.BigDecimalUtils.showSNormal(this)
}

inline fun String?.showFormatEGT(): String {
    if (this == null) {
        return "0"
    }
    return com.zimozi.assessment.util.BigDecimalUtils.showSNormal(
        com.zimozi.assessment.util.BigDecimalUtils.round(this, 0).toPlainString())
}

inline fun String?.showPlus(value: Any): String {
    if (this == null) {
        return "0"
    }
    return com.zimozi.assessment.util.BigDecimalUtils.showSNormal(
        com.zimozi.assessment.util.BigDecimalUtils.add(
            this,
            value.toString()
        ).toPlainString()
    )
}

inline fun String?.showSub(value: Any): String {
    if (this == null) {
        return "0"
    }
    return com.zimozi.assessment.util.BigDecimalUtils.showSNormal(
        com.zimozi.assessment.util.BigDecimalUtils.sub(
            this,
            value.toString()
        ).toPlainString()
    )
}

inline fun String?.showDiv(value: Any): String {
    if (this == null) {
        return "0"
    }
    return com.zimozi.assessment.util.BigDecimalUtils.showSNormal(
        com.zimozi.assessment.util.BigDecimalUtils.div(
            this,
            value.toString()
        ).toPlainString()
    )
}

inline fun String?.showMul(value: Any): String {
    if (this == null) {
        return "0"
    }
    return com.zimozi.assessment.util.BigDecimalUtils.showSNormal(
        com.zimozi.assessment.util.BigDecimalUtils.mul(
            this,
            value.toString()
        ).toPlainString()
    )
}

fun String?.addCurrencySymbol(): String {
    if (this == null) {
        return "$0"
    }
    return "$$this"
}

fun String?.toNumberFormat(): String {
    if (this == null) {
        return "0.0"
    }
    return this.replace(",", "")
}
