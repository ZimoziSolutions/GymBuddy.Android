package com.zimozi.assessment.data.networking

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class EverestCallbackWrapper<T>(private val errorTracking: MutableLiveData<ErrorCallBack>) :
    Observer<T> {

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(value: T) {
        var responseError: HttpEverestError? = null
        try {
            responseError = Gson().fromJson(value.toString(), HttpEverestError::class.java)
        } catch (e: Exception) {
        }
        if (responseError != null) {
            if (responseError.error_code != 0) {
                errorTracking.value =
                    HttpExceptionError(
                        responseError.error_message,
                        responseError.error_code
                    )
            } else {
                onHandleSuccess(value)
            }
        } else {
            onHandleSuccess(value)
        }
    }

    override fun onError(t: Throwable) {
        if (t is HttpException) {
            var responseError: HttpEverestError? = null
            try {
                responseError = Gson().fromJson(
                    t.response()?.errorBody()?.string(),
                    HttpEverestError::class.java
                )
            } catch (e: Exception) {
            }
            if (responseError != null) {
                if (responseError.error_code != 0) {
                    errorTracking.value =
                        HttpExceptionError(
                            responseError.error_message,
                            responseError.error_code
                        )
                } else {
                    errorTracking.value =
                        HttpExceptionError(
                            "Network abnormal",
                            -1
                        )
                }
            } else {
                errorTracking.value =
                    HttpExceptionError("Network abnormal", -1)
            }
        } else if (t is SocketTimeoutException) {
            errorTracking.value =
                TimeOutError("Oops! There seems to be a technical issue. Please check your connectivity or try again later.")
        } else if (t is IOException) {
            errorTracking.value =
                NoNetworkError("No network connection")
        } else {
            errorTracking.value =
                UnKnownError("Unknown error:${t.localizedMessage}")
        }
    }

    override fun onComplete() {
        onCompleteRequest()
    }

    private fun onHandleSuccess(t: T?) {
        onSuccess(t)
    }

    protected abstract fun onSuccess(t: T?)
    protected abstract fun onCompleteRequest()
}