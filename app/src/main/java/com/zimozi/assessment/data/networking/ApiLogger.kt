package com.zimozi.assessment.data.networking

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.zimozi.assessment.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

class ApiLogger : HttpLoggingInterceptor.Logger {
    private val LOGGING_ENABLED = BuildConfig.DEBUG

    fun largeLog(tag: String, content: String) {
            if (LOGGING_ENABLED) {
                if (content.length > 4000) {
                    Log.d(tag, content.substring(0, 4000))
                    //largeLog(tag, content.substring(4000))
                } else {
                    Log.d(tag, content)
                }
            }
    }

    override fun log(message: String) {
        if (LOGGING_ENABLED) {
            val logName = "ApiLogger"
            if (message.startsWith("{") || message.startsWith("[")) {
                try {
                    val prettyPrintJson = GsonBuilder().setPrettyPrinting()
                            .create().toJson(JsonParser().parse(message))
//                Log.d(logName, prettyPrintJson)
                    largeLog(logName, prettyPrintJson)
                } catch (m: JsonSyntaxException) {
                    Log.d(logName, message)
                } catch(m : Exception) {
                    m.printStackTrace()
                }
            } else {
                Log.d(logName, message)
                return
            }
        }
    }
}