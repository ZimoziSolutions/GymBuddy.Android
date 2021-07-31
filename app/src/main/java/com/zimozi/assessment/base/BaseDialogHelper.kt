package com.zimozi.assessment.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog

abstract class BaseDialogHelper {

    abstract val dialogView: View?
    abstract val builder: AlertDialog.Builder

    //  required bools
    open var cancelable: Boolean = false
    open var isBackGroundTransparent: Boolean = false
    open var isFullHeight: Boolean = false
    open var isBottomGravity: Boolean = false

    //  dialog
    open var dialog: AlertDialog? = null

    //  dialog create
    open fun create(): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()

        if (isFullHeight) {
            dialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
        } else {
            dialog?.window?.setGravity(Gravity.CENTER)
        }
        if (isBottomGravity) {
            dialog?.window?.setGravity(Gravity.BOTTOM)
        }
        //  very much needed for customised dialogs
        if (isBackGroundTransparent)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog!!
    }

    //  cancel listener
    open fun onDismissListener(func: () -> Unit): AlertDialog.Builder? =
        builder.setOnDismissListener {
            func()
        }
}