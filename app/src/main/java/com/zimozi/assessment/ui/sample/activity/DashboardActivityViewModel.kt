package com.zimozi.assessment.ui.sample.activity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.zimozi.assessment.base.BaseViewModel
import com.zimozi.assessment.data.model.GymDataResponse
import com.zimozi.assessment.util.extension.loge
import javax.inject.Inject

class DashboardActivityViewModel @Inject constructor() : BaseViewModel() {


    val gymDataResponseLiveData = MutableLiveData<GymDataResponse>()
    fun getGymData(context: Context) {

        var data = GymDataResponse()
        val gson = Gson()
        data = gson.fromJson(
            dataRepository.getGymData(context).value,
            GymDataResponse::class.java
        )

        gymDataResponseLiveData.value = data

    }


}