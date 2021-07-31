package com.zimozi.assessment.util

import androidx.databinding.BaseObservable
import kotlin.reflect.KProperty

/*  Sample Use

    @get:Bindable
    var firstName: String by bindable("", BR.firstName)

    @get:Bindable
    var lastName: String by bindable("", BR.lastName)

    val displayName: String
        @Bindable(value = ["firstName", "lastName"])
        get() = resourceProvider.getString(R.string.display_name, firstName, lastName)
 */

class BindableDelegate<in R : BaseObservable, T : Any>(
    private var value: T,
    private val bindingId: Int
) {
    operator fun getValue(receiver: R, property: KProperty<*>): T = value

    operator fun setValue(receiver: R, property: KProperty<*>, value: T) {
        this.value = value
        receiver.notifyPropertyChanged(bindingId)
    }
}

fun <R : BaseObservable, T : Any> bindable(value: T, bindingId: Int): BindableDelegate<R, T> =
    BindableDelegate(value, bindingId)