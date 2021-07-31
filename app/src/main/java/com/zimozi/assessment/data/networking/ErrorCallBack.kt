package com.zimozi.assessment.data.networking

sealed class ErrorCallBack {
    abstract val message: String
}

data class HttpExceptionError(override val message: String, val code: Int) : ErrorCallBack()
data class TimeOutError(override val message: String) : ErrorCallBack()
data class NoNetworkError(override val message: String) : ErrorCallBack()
data class UnKnownError(override val message: String) : ErrorCallBack()
