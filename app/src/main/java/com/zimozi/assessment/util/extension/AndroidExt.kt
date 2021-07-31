package com.zimozi.assessment.util.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.text.Spannable
import android.text.style.URLSpan
import android.view.TouchDelegate
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NewApi")
fun Context.getCurrentActivity(): ComponentName? {
    val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return am.getRunningTasks(1)[0].topActivity
}

fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.name.equals(service.service.className)) {
            return true
        }
    }
    return false
}

fun Context.hasNetworkConnection(): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

fun Context.getStatusBarHeight(): Int {
    // 获得状态栏高度
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Activity.shareLinkToSocial(content: String?) {
    content?.let {
        ShareCompat.IntentBuilder.from(this)
            .setType("text/plain")
            .setText(content)
            .startChooser()
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun Date.convertToDateTimeDisplay(): String {
    val writeFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return writeFormat.format(this)
}

//===========================
//fun String.loadHTML(textView: TextView): Spanned {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        return Html.fromHtml(
//            this,
//            Html.FROM_HTML_MODE_LEGACY,
//            GlideImageGetter(textView),
//            null
//        )
//    } else {
//        @Suppress("DEPRECATION")
//        return Html.fromHtml(this, GlideImageGetter(textView), null)
//    }
//}

fun String.getNullableValue(): String? {
    if (this.isNullOrEmpty()) {
        return null
    }
    return this
}

fun Boolean.toObservableField() = ObservableBoolean(this)

fun Float.formatString(): String {
    return if (this % 1 == 0f) {
        String.format(Locale.US, "%.0f", this)
    } else {
        String.format(Locale.US, "%.2f", this)
    }
}

fun Float.formatDecimalString(): String {
    return String.format(Locale.US, "%.2f", this)
}

fun String.Remove0Character(): String {
    if (this.isNotEmpty() && this.last().toString().equals("0")) {
        return this.substring(0, this.length - 1)
    }
    return this
}

fun Spannable.removeUnderLine(): Spannable {
    val pText = this
    val spans = pText.getSpans(0, pText.length, URLSpan::class.java)
    for (span in spans) {
        val start = pText.getSpanStart(span)
        val end = pText.getSpanEnd(span)
        pText.removeSpan(span)
        //pText.setSpan(URLSpanNoUnderline(span.url), start, end, 0)
    }
    return pText
}

//fun Context.changeLanguage(languageCode: String, clz: Class<*>) {
//    val resources = this.resources
//    val dm = resources.displayMetrics
//    val config = resources.configuration
//    LanguageUtil.saveLanguage(languageCode)
//    val locale = LanguageUtil.getLanguageLocale(languageCode)
//    when {
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
//            val localeList = LocaleList(locale)
//            LocaleList.setDefault(localeList)
//            config.setLocales(localeList)
//            Locale.setDefault(locale)
//            this.createConfigurationContext(config)
//        }
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
//            config.setLocale(locale)
//            this.createConfigurationContext(config)
//        }
//        else -> {
//            config.locale = locale
//            resources.updateConfiguration(config, dm)
//        }
//    }
//
//    val intent = Intent(this, clz)
//    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//    this.startActivity(intent)
//}

/**
 * dependant multiple live data to observable
 */
inline fun <T> dependantLiveData(
    vararg dependencies: LiveData<out Any>,
    defaultValue: T? = null,
    crossinline mapper: () -> T?
): LiveData<T> =
    MediatorLiveData<T>().also { mediatorLiveData ->
        val observer = Observer<Any> { mediatorLiveData.value = mapper() }
        dependencies.forEach { dependencyLiveData ->
            mediatorLiveData.addSource(dependencyLiveData, observer)
        }
    }.apply { value = defaultValue }

fun View.preventDoubleClick() {
    this.isEnabled = false
    Handler().postDelayed({ this.isEnabled = true }, 1000)
}


fun View.increaseViewTouchArea(extraSpace: Int) {
    val parent = this.parent as View
    parent.post {
        val touchableArea = Rect()
        this.getHitRect(touchableArea)
        touchableArea.top -= extraSpace
        touchableArea.bottom += extraSpace
        touchableArea.left -= extraSpace
        touchableArea.right += extraSpace
        parent.touchDelegate = TouchDelegate(touchableArea, this)
    }
}