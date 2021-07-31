package com.zimozi.assessment.data.networking

import androidx.lifecycle.MutableLiveData
import io.reactivex.observers.DisposableObserver
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class CallbackWrapper1<T>(private val errorTracking: MutableLiveData<ErrorCallBack>?) : DisposableObserver<T>() {

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(t: Throwable) {
        if (t is HttpException) {
            errorTracking?.value = HttpExceptionError(
                getErrorMessage(t.response()?.errorBody()),
                t.code()
            )
        } else if (t is SocketTimeoutException) {
            errorTracking?.value =
                TimeOutError("Oops! There seems to be a technical issue. Please check your connectivity or try again later.")
        } else if (t is IOException) {
            errorTracking?.value =
                NoNetworkError("No network connection")
        } else {
            errorTracking?.value =
                UnKnownError("Unknown error:${t.localizedMessage}")
        }
    }

    override fun onComplete() {
        onCompleteRequest()
    }

    protected abstract fun onSuccess(t: T)
    protected abstract fun onCompleteRequest()


    private fun getErrorMessage(responseBody: ResponseBody?): String {
        try {
            val jsonObject = JSONObject(responseBody?.string())
            return if (jsonObject.has("message")) jsonObject.getString("message") else if (jsonObject.has("error")) jsonObject.getString(
                "error"
            ) else jsonObject.toString()
        } catch (e: Exception) {
            return e.localizedMessage
        }

    }
//
//    private fun getErrorCode(responseBody: ResponseBody): Int {
//        try {
//            val jsonObject = JSONObject(responseBody.string())
//            return jsonObject.getInt("code")
//        } catch (e: Exception) {
//            return 0
//        }
//
//    }
}