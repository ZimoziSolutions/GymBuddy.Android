package com.zimozi.assessment.util

import android.content.Context
import androidx.lifecycle.LifecycleObserver
import com.zimozi.assessment.data.preferences.PreferencesHelper
import javax.inject.Inject

class AppLockObserver @Inject constructor(
    val context: Context,
    val preferencesHelper: PreferencesHelper
) : LifecycleObserver {

//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    fun onEnterForeground() {
//        FAUtils.logEvent(context, FAUtils.EventName.KEEP_USING_APP.value)
//        // Clear daily deposit and withdrawal data if in past
//        val dailyDepositData = preferencesHelper.getDailyDeposit()
//        dailyDepositData?.let {
//            if (DateUtils.isYesterday(it.time)) {
//                preferencesHelper.clearDailyDepositData()
//            }
//        }
//        val dailyDailyWithdrawalData = preferencesHelper.getDailyWithdrawal()
//        dailyDailyWithdrawalData?.let {
//            if (DateUtils.isYesterday(it.time)) {
//                preferencesHelper.clearDailyWithdrawalData()
//            }
//        }
////        if (loginManager.isLogined() && preferencesHelper.getIsEnableBiometric()) {
////            if (goldFinger.hasFingerprintHardware() && goldFinger.hasEnrolledFingerprint()) {
////                LogUtil.debug("Start biometric login")
////                context.startActivity(
////                    Intent(context, LoginBiometricActivity::class.java).apply {
////                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
////                        putExtra(APP_SWITCHING, true)
////                    }
////                )
////            }
////        }
//    }
}