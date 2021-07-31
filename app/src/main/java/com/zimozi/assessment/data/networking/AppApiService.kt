package com.zimozi.assessment.data.networking

import com.zimozi.assessment.data.model.*

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface AppApiService {





    @GET("userExists")
    fun getUser( @Query("phone") phone: String):Observable<UserResponse>

     }