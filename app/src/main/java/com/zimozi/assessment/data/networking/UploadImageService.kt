package com.zimozi.assessment.data.networking

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadImageService {

    @Multipart
    @POST("fe-ex-api/common/aws_upload_img")
    fun uploadImgAWS(@Part filePart: MultipartBody.Part): Observable<HttpResult<JsonObject>>
}