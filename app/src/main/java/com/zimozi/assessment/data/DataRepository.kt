package com.zimozi.assessment.data


import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.zimozi.assessment.data.networking.AppApiService
import com.zimozi.assessment.data.preferences.PreferencesHelper
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.zimozi.assessment.R
import com.zimozi.assessment.data.model.GymDataResponse
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class DataRepository @Inject constructor(
    val apiService: AppApiService,
    val preferencesHelper: PreferencesHelper,
    val gson: Gson
) {


    fun getGymData(context: Context): MutableLiveData<String> {

        var inputStream: InputStream? = null
        val builder = StringBuilder()
        val livedata = MutableLiveData<String>()
        try {
            var jsonString: String? = null
            inputStream = context.getResources().openRawResource(R.raw.data)
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, "UTF-8")
            )
            while (bufferedReader.readLine().also { jsonString = it } != null) {
                builder.append(jsonString)
            }
        } finally {
            inputStream?.close()
        }

        livedata.value = String(builder)
        return livedata
    }





    private fun toRequestBody(params: Map<String, String>): RequestBody {
        return RequestBody.create(
            MediaType.parse("application/json;charset=utf-8"),
            JSONObject(params).toString()
        )
    }

    private fun toRequestBodyAny(params: Map<String, Any>): RequestBody {
        return RequestBody.create(
            MediaType.parse("application/json;charset=utf-8"),
            JSONObject(params).toString()
        )
    }


    private fun toRequestLocationAny(params: Map<String, Any>): RequestBody {
        return RequestBody.create(
            MediaType.parse("application/json;charset=utf-8"),
            JSONObject(params).toString()
        )
    }
}



