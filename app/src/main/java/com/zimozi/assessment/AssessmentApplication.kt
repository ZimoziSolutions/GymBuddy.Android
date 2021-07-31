package com.zimozi.assessment

import android.app.Activity
import android.app.Service
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import com.zimozi.assessment.util.AppLockObserver
import com.zimozi.assessment.di.injector.AppInjector

import javax.inject.Inject


class AssessmentApplication : MultiDexApplication(), HasActivityInjector, HasServiceInjector{

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mServiceDispatchingAndroidInjector: DispatchingAndroidInjector<Service>

    @Inject
    lateinit var appLockObserver: AppLockObserver

    companion object {
        lateinit var appContext: Context
        lateinit var instance: Context private set
    }

    override fun onCreate() {

        super.onCreate()
       instance = this
      //  OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.WARN)


        appContext = applicationContext


        AppInjector.init(this)



        // Initialize Fabric with the debug-disabled crashlytics.
//        Fabric.with(this, crashlyticsKit)
      //  Fabric.with(this, crashlyticsKit) // setup font calligaphy


//        ViewPump.init(
//            ViewPump.builder()
//                .addInterceptor(
//                    CalligraphyInterceptor(
//                        CalligraphyConfig.Builder()
//                            .setDefaultFontPath(getString(R.string.Montserrat_Regular))
//                            .setFontAttrId(R.attr.fontPath)
//                            .build()
//                    )
//                )
//                .build()
//        )
        // OneSignal Initialization
//        OneSignal.startInit(this)
//            // .setNotificationOpenedHandler(NotificationOpenedHandler())
//            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//            .unsubscribeWhenNotificationsAreDisabled(true)
//            .init()

      //  initFresco()

        ProcessLifecycleOwner.get().lifecycle.addObserver(appLockObserver)

        // OneSignal
        if (BuildConfig.DEBUG) {
           // OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.NONE)
        }
    }

//    private fun initFresco() {
//        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val imagePipelineConfig = ImagePipelineConfig
//            .newBuilder(applicationContext)
//            .setBitmapMemoryCacheParamsSupplier(
//                LollipopBitmapMemoryCacheParamsSupplier(
//                    activityManager
//                )
//            )
//            .setDownsampleEnabled(true)
//            .setResizeAndRotateEnabledForNetwork(true)
//            .build()
//
//        Fresco.initialize(applicationContext, imagePipelineConfig)
//    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return mServiceDispatchingAndroidInjector
    }





        // Logging set to help debug issues, remove before releasing your app.

    }

