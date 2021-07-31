package com.zimozi.assessment.util

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.zimozi.assessment.AssessmentApplication
import com.zimozi.assessment.R


object DisplayUtils {

    fun getWidthHeight(context: Context): IntArray {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val width = wm.defaultDisplay.width
        val height = wm.defaultDisplay.height

        return intArrayOf(width, height)
    }

    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun getDeviceDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    /**
     * @param view
     * @param text
     * @param isSuc 是否是成功的状态
     */

}
