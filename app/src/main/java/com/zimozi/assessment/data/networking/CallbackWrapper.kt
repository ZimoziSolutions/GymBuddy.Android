package com.zimozi.assessment.data.networking

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class CallbackWrapper<T>(private val errorTracking: MutableLiveData<ErrorCallBack>) :
    Observer<HttpResult<T>> {

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(value: HttpResult<T>) {
        if (value.isSuccess) {
            if (value.hasData()) {
                onHandleSuccess(value.data)
            } else {
                onHandleSuccess(null)
            }
        } else {
            errorTracking.value =
                HttpExceptionError(
                    value.msg,
                    Integer.parseInt(value.code)
                )
        }
    }

    override fun onError(t: Throwable) {
        if (t is HttpException) {
            errorTracking.value =
                HttpExceptionError("Network abnormal", -1)
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