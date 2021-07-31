package com.zimozi.assessment.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GymDataResponse(

    @field:SerializedName("gyms")
    var gyms: ArrayList<GymsItem?>? = null,

    @field:SerializedName("drawables")
    var drawables: ArrayList<DrawableItem>? = null


) : Parcelable

@Parcelize
data class DrawableItem(

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("favorite")
    var favorite: Boolean? = false
) : Parcelable

@Parcelize
data class GymsItem(

    @field:SerializedName("date")
    var date: String? = null,

    @field:SerializedName("popular_clasess")
    var popularClasess: ArrayList<PopularClasessItem>? = null,

    @field:SerializedName("price")
    var price: Int? = null,

    @field:SerializedName("rating")
    var rating: Double? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("favorite")
    var favorite: Boolean? = false
) : Parcelable

@Parcelize
data class PopularClasessItem(

    @field:SerializedName("price")
    var price: Int? = null,

    @field:SerializedName("location")
    var location: String? = null,

    @field:SerializedName("time")
    var time: String? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("favorite")
    var favorite: Boolean? = false


) : Parcelable
