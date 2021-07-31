package com.zimozi.assessment.util

import android.content.Context
import android.telephony.TelephonyManager

object CountryUtils {

    fun getDetectedCountryCode(context: Context): String {
        return detectSIMCountry(context)
            ?: detectNetworkCountry(context)
            ?: detectLocaleCountry(context)
            ?: "sg"
    }

    private fun detectSIMCountry(context: Context): String? {
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountryISO = telephonyManager.simCountryIso
            if (simCountryISO.isNullOrEmpty()) {
                return null
            }
            return simCountryISO.toLowerCase()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    private fun detectNetworkCountry(context: Context): String? {
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val networkCountryISO = telephonyManager.networkCountryIso
            if (networkCountryISO.isNullOrEmpty()) {
                return null
            }
            return networkCountryISO.toLowerCase()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    private fun detectLocaleCountry(context: Context): String? {
        try {
            val localeCountryISO = context.resources.configuration.locale.country
            if (localeCountryISO.isNullOrEmpty()) {
                return null
            }
            return localeCountryISO.toLowerCase()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}