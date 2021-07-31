package com.zimozi.assessment.data.preferences

import android.content.Context
import android.content.SharedPreferences

class PrefConfig(context: Context) {
    private val ACCESS_KEY = "ACCESS_KEY"
    private val SECRET_KEY = "SECRET_KEY"
    private val prefs: SharedPreferences = context.getSharedPreferences("NeedApp", Context.MODE_PRIVATE)


    var accessKey: String
        get() = prefs.getString(ACCESS_KEY, "")!!
        set(value) = prefs.edit().putString(ACCESS_KEY, value).apply()

    var secretKey: String
        get() = prefs.getString(SECRET_KEY, "")!!
        set(value) = prefs.edit().putString(SECRET_KEY, value).apply()
}