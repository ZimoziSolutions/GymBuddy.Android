package com.zimozi.assessment.data.preferences

import android.content.SharedPreferences
import com.google.gson.Gson

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(
    val preferences: SharedPreferences,
    val gson: Gson
) {
    companion object {
        const val TOKEN = "TOKEN"
        const val IS_FIRST_LOGIN = "IS_FIRST_LOGIN"

    }

    private fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }


    private fun getString(key: String): String {
        return preferences.getString(key, "")!!
    }

    fun putStringNew(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getStringNew(key: String): String {
        return preferences.getString(key, "")!!
    }

    fun putBooleanNew(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanNew(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    private fun putInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    private fun getInt(key: String): Int {
        return preferences.getInt(key, -1)
    }

    private fun putBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    private fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, true)
    }

    fun clear() {
        // Do not clear config app
        preferences.edit().clear().apply()
        //    setIsFirstLogin(false)
    }

    fun saveToken(token: String?) {
        putString(TOKEN, token ?: "")
    }

    fun getToken(): String {
        return getString(TOKEN)
    }







}