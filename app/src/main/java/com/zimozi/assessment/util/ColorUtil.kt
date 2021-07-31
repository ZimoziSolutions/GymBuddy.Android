package com.zimozi.assessment.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.zimozi.assessment.AssessmentApplication
import com.zimozi.assessment.R

/**
 * @Author: Bertking
 * @Date：2019/3/9-2:15 PM
 * @Description:
 */
object ColorUtil {

    /**
     * 绿涨红跌
     */
    const val GREEN_RISE = 0
    /**
     * 红涨绿跌
     */
    const val RED_RISE = 1

    fun getColor(context: Context, colorId: Int) =
        ContextCompat.getColor(context, colorId)

    fun getColor(colorId: Int) = getColor(
        AssessmentApplication.appContext,
        colorId
    )

    /**
     *获取主要颜色(红绿)
     * @param isRise 是否是上涨状态
     */
    fun getMainColorType(colorSelect: Int = GREEN_RISE, isRise: Boolean = true): Int {
        val mainGreen =
            getColor(R.color.text_vividGreen)
        val mainRed = getColor(R.color.bg_deep_red)
        return if (colorSelect == GREEN_RISE) {
            if (isRise) {
                mainGreen
            } else {
                mainRed
            }
        } else {
            if (isRise) {
                mainRed
            } else {
                mainGreen
            }
        }
    }
}