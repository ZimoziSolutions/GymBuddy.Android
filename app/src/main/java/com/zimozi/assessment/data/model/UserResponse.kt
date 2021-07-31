package com.zimozi.assessment.data.model

import com.google.gson.annotations.SerializedName


data class UserResponse (
    @SerializedName("userExists")
    val userExists: Boolean ?= null,
    @SerializedName("data")
    val data: Data ?=null
)


data class Data (
    @SerializedName("_id")
    val _id: String ?=null,
    @SerializedName("name")
    val name: String ?=null,
    @SerializedName("email")
    val email: String ?=null,
    @SerializedName("gender")
    val gender: String ?=null,
    @SerializedName("profileImage")
    val profileImage: List<Any?> ?=null,
    @SerializedName("location")
    val location: Any? = null,
    @SerializedName("bloodGroup")
    val bloodGroup: String ?=null
)
