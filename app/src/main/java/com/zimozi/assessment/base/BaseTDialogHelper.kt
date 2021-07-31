package com.zimozi.assessment.base

import android.content.Context
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.timmy.tdialog.TDialog
import com.timmy.tdialog.listener.OnBindViewListener
import com.zimozi.assessment.R

abstract class BaseTDialogHelper(val ctx: Context) {

    abstract val dialogView: Int?
    abstract val onBindView: OnBindViewListener?

    abstract val gravity: Int?

    abstract val screenWidthAspect: Float?

    //  dialog
    lateinit var builder: TDialog.Builder
    open var dialog: TDialog? = null

    private var cancelCallback: (() -> Unit)? = null

    //  dialog create
    fun create(): TDialog {

        val fragmentManager =
            when (ctx) {
                is Fragment -> {
                    com.zimozi.assessment.util.LogUtil.d("Show with fragment")
                    ctx.fragmentManager
                }
                is AppCompatActivity -> {
                    com.zimozi.assessment.util.LogUtil.d("Show with Activity")
                    ctx.supportFragmentManager
                }
                else -> null
            }

        builder = TDialog.Builder(fragmentManager)
        builder
            .setScreenWidthAspect(ctx, screenWidthAspect ?: 1f)
            .setGravity(gravity ?: Gravity.BOTTOM)
            .setDimAmount(0.8f)
            .setCancelableOutside(false)


        dialogView?.let {
            builder.setLayoutRes(it)
        }

        onBindView?.let {
            builder.setOnBindViewListener(it)
        }

        dialog = builder.create()
        return dialog!!
    }

    fun setCancelClickListener(func: (() -> Unit)? = null): BaseTDialogHelper {
        cancelCallback = func
        return this
    }

    open fun show(): TDialog {
        return create().show()
    }
}