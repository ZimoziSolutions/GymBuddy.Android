package com.zimozi.assessment.data.networking

data class HttpEverestError(
    val error_message: String,
    val account_id: String,
    val error_code: Int
)