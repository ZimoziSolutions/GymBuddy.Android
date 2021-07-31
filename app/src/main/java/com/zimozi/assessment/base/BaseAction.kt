package com.zimozi.assessment.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zimozi.assessment.data.networking.*
import com.zimozi.assessment.R
import com.zimozi.assessment.ui.sample.activity.SampleActivity
import com.zimozi.assessment.util.DisplayUtils


open class BaseAction constructor(val context: Context) {

    var progressDialog: ProgressDialog? = null

    var viewModel: BaseViewModel? = null


    open fun initObserverViewModel(owner: LifecycleOwner, viewModel: BaseViewModel?) {
        this.viewModel = viewModel
        viewModel?.let {
            it.errorState.observe(owner, Observer {
                it?.let { onErrorRequest(it as ErrorCallBack) }
            })
            it.loadingState.observe(owner, Observer { it ->
                it?.let {
                    if (it) {
                        showLoading()
                    } else {
                        hideLoading()
                    }
                }
            })
        }
    }

    open fun onErrorRequest(error: ErrorCallBack) {
        com.zimozi.assessment.util.LogUtil.e("Error: ${error.message}")
        when (error) {
            is HttpExceptionError -> {
                when (error.code) {
                    10050 -> {
                        when (context) {
                            is SampleActivity -> {
                                showErrorDialog(error.message)
                            }
                            else -> {
                                viewModel?.logOut()
                                startActivity(
                                    SampleActivity::class.java,
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                )
                            }
                        }
                    }
                    10021, 10002, 3 -> {

                        when (context) {

                            is SampleActivity -> {
                                showErrorDialog(error.message)
                            }
                            else -> {
                                // go to login biometric or login

                                    viewModel?.logOut()
                                    startActivity(
                                        SampleActivity::class.java,
                                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    )

                            }
                        }
                    }
                    else -> showErrorDialog(error.message)
                }
            }
            is TimeOutError -> showErrorDialog(R.string.error_unknown)
            is NoNetworkError -> showErrorDialog(R.string.error_no_network_connection)
            is UnKnownError -> showErrorDialog(error.message)
        }
    }

//    private fun isGesturePwdEnabled(): Boolean {
//        return !viewModel?.dataRepository?.loginManager?.userInfoData?.gesturePwd.isNullOrEmpty()
//    }

    open fun showErrorDialog(msg: String) {
        // context.showNoticeErrorDialog(msg) {}.show()

    }

    open fun showErrorDialog(resId: Int) {

        // context.showNoticeErrorDialog() {}.show()
    }

    open fun createProgressDialog(message: String): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setMessage(message)
        return progressDialog
    }

    open fun showLoading() {
        if (progressDialog == null || progressDialog?.isShowing == false) {
            hideLoading()
            progressDialog = createProgressDialog(context.getString(R.string.loading))
            progressDialog?.show()
        }
    }

    open fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    fun startActivity(clz: Class<*>) {
        context.startActivity(Intent(context, clz))
    }

    fun startActivityForResult(clz: Class<*>) {
        context.startActivity(Intent(context, clz))
    }

    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(context, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        context.startActivity(intent)
    }

    fun startActivity(clz: Class<*>, flags: Int? = null) {
        val intent = Intent(context, clz)
        if (flags != null) {
            intent.addFlags(flags)
        }
        context.startActivity(intent)
    }

    fun popFragment() {
        with(context as AppCompatActivity) {
            this.supportFragmentManager.popBackStack()
        }
    }
}