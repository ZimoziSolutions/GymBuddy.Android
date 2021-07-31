package com.zimozi.assessment.util.FirebaseAuthenticationHelper

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.zimozi.assessment.R

abstract class CallbackFirebaseWrapper<T>(val context: Context?) : OnCompleteListener<T> {

    override fun onComplete(task: Task<T>) {
        if (task.isSuccessful) {
            onSuccess(task)
        } else {
            if (context != null) {
                when (task.exception) {
                    is FirebaseNetworkException -> {
                        onError(context.getString(R.string.error_no_network_connection))
                    }
                    is FirebaseAuthException -> {
                        val firebaseAuthException = task.exception as FirebaseAuthException
                        com.zimozi.assessment.util.LogUtil.i("Error firebase: ${firebaseAuthException.errorCode}")
                        when (firebaseAuthException.errorCode) {
                            "ERROR_INVALID_EMAIL" -> {
                                onError(context.getString(R.string.error_invalid_email))
                            }
                            "ERROR_USER_NOT_FOUND" -> {
                                onError(context.getString(R.string.error_email_not_exist))
                            }
                            "ERROR_WRONG_PASSWORD" -> {
                                onError(context.getString(R.string.error_wrong_password))
                            }
                            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                                onError(context.getString(R.string.error_email_already_exist))
                            }
                            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                                onError(context.getString(R.string.error_email_already_exist))
                            }
                            else -> {
                                onError(context.getString(R.string.error_unknown))
                            }
                        }
                    }
                    else -> {
                        onError(context.getString(R.string.error_unknown))
                    }
                }
            } else {
                onError("")
            }
        }
    }


    protected abstract fun onSuccess(task: Task<T>)
    protected abstract fun onError(messageError: String)

}