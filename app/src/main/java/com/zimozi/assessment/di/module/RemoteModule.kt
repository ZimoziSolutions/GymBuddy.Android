package com.zimozi.assessment.di.module

import android.content.Context
import com.zimozi.assessment.BuildConfig
import com.zimozi.assessment.data.networking.ApiLogger
import com.zimozi.assessment.data.networking.AppApiService
import com.zimozi.assessment.data.networking.converter.ResponseConverterFactory
import com.zimozi.assessment.data.preferences.PreferencesHelper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class RemoteModule {

    companion object {
        var url: String = ""
    }

    //添加header
//    private inner class HeaderInterceptor(val context: Context, val loginManager: LoginManager) :
//        Interceptor {
//
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val language = when {
//                SystemUtils.isZh() -> "zh_CN"
//                SystemUtils.isMn() -> "mn_MN"
//                SystemUtils.isRussia() -> "ru_RU"
//                SystemUtils.isKorea() -> "ko_KR"
//                SystemUtils.isJapanese() -> "ja_JP"
//                SystemUtils.isTW() -> "el_GR"
//                SystemUtils.isVietNam() -> "vi_VN"
//                else -> "en_US"
//            }
//
//            var originalRequest: Request
//
//            val packageManager = context.packageManager
//            // getPackageName()是你当前类的包名，0代表是获取版本信息
//            val packInfo = packageManager.getPackageInfo(context.packageName, 0)
//            LogUtil.d(
//                "=======http:======" + loginManager.isLogined() + "：token---" + loginManager.getToken() + ":ht-token:"
//            )
//            if (loginManager.isLogined()) {
//
//                originalRequest = chain.request()
//                    .newBuilder()
//                    .header("Content-Type", "application/json;charset=utf-8")
//                    .header("Build-CU", packInfo.versionCode.toString())
//                    .header("SysVersion-CU", SystemUtils.getSystemVersion())
//                    .header("SysSDK-CU", Build.VERSION.SDK_INT.toString())
//                    .header("Channel-CU", "")
//                    .header("Mobile-Model-CU", SystemUtils.getSystemModel())
//                    .header("UUID-CU:APP", "xxxxxxxxxxxxxxxx")
//                    .header("Platform-CU", "android")
//                    .header("Network-CU", NetworkUtils.getNetType(context))
//                    .header("exchange-language", language)
//                    .header("exchange-token", loginManager.getToken() ?: "")
//                    .header("exchange-client", "app")
//                    .build()
//            } else {
//                originalRequest = chain.request()
//                    .newBuilder()
//                    .header("Content-Type", "application/json;charset=utf-8")
//                    .header("Build-CU", packInfo.versionCode.toString())
//                    .header("SysVersion-CU", SystemUtils.getSystemVersion())
//                    .header("SysSDK-CU", Build.VERSION.SDK_INT.toString())
//                    .header("Channel-CU", "")
//                    .header("Mobile-Model-CU", SystemUtils.getSystemModel())
//                    .header("UUID-CU:APP", "xxxxxxxxxxxxxxxx")
//                    .header("Platform-CU", "android")
//                    .header("Network-CU", NetworkUtils.getNetType(context))
//                    .header("exchange-language", language)
//                    .header("exchange-client", "app")
//                    .build()
//            }
//
//            return chain.proceed(originalRequest)
//        }
//    }
//
    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context, preferencesHelper: PreferencesHelper): OkHttpClient {
       // var array = arrayOf(context.resources.assets.open("cert.cer"))
       // val sslParams = HttpsUtils.getSslSocketFactory(array, null, null)
        return OkHttpClient.Builder()
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
            // .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .addInterceptor {
                val request = it.request()
                val newUrl: HttpUrl
                newUrl = when {
                    url.isNotEmpty() -> {
                        val scheme = HttpUrl.parse(url)?.scheme() ?: ""
                        val host = HttpUrl.parse(url)?.host() ?: ""
                        com.zimozi.assessment.util.LogUtil.d("Request: scheme: $scheme, Host:$host")
                        request.url().newBuilder()
                            .scheme(scheme)
                            .host(host)
                            .build()
                    }
                    else -> request.url().newBuilder()
                        .build()
                }
                it.proceed(
                    request.newBuilder()
                        .url(newUrl)
                        .addHeader("Authorization", preferencesHelper.getToken())
                        .build()
                )

//                 val newRequest = chain.request().newBuilder()
//                     .addHeader("Authorization", preferencesHelper.getToken())
//                     .build()
//                 chain.proceed(newRequest)
            }
            .addInterceptor(HttpLoggingInterceptor(ApiLogger()).setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
             .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ResponseConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
//
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AppApiService =
        retrofit.create(AppApiService::class.java)
//
//    @Provides
//    @Singleton
//    fun provideAppPointApiService(gson: Gson): AppPointApiService {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BuildConfig.pointBaseUrl) // will replace by another url
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(OkHttpClient.Builder()
//                .addInterceptor {
//                    val newRequest = it.request().newBuilder()
//                        .addHeader("Authorization", "dXNlcm5hbWU6cGFzc3dvcmQ=")
//                        .build()
//                    it.proceed(newRequest)
//                }
//                .addInterceptor(
//                    HttpLoggingInterceptor(ApiLogger()).setLevel(
//                        HttpLoggingInterceptor.Level.BODY
//                    )
//                )
//                .build())
//            .build()
//        return retrofit.create(AppPointApiService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideAppBindEmailService(okHttpClient: OkHttpClient, gson: Gson): AppBindEmailService =
//        Retrofit.Builder()
//            .baseUrl(BuildConfig.bindEmailUrl)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(okHttpClient)
//            .build()
//            .create(AppBindEmailService::class.java)
//
//
//    @Provides
//    @Singleton
//    fun provideAppBankTransferService(okHttpClient: OkHttpClient): AppBankTransferService =
//        Retrofit.Builder()
//            .baseUrl(BuildConfig.bankTransferUrl)
//            .addConverterFactory(ResponseConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(okHttpClient)
//            .build()
//            .create(AppBankTransferService::class.java)
//
//    @Provides
//    @Singleton
//    fun provideAppPrimaryMarketService(okHttpClient: OkHttpClient): AppPrimaryMarketService =
//        Retrofit.Builder()
//            .baseUrl(BuildConfig.primaryMarketUrl)
//            .addConverterFactory(ResponseConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(okHttpClient)
//            .build()
//            .create(AppPrimaryMarketService::class.java)
//
//    @Provides
//    @Singleton
//    fun provideIOEService(okHttpClient: OkHttpClient): IOEAppService =
//        Retrofit.Builder()
//            .baseUrl(BuildConfig.ioeUrl)
//            .addConverterFactory(ResponseConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(okHttpClient)
//            .build()
//            .create(IOEAppService::class.java)
//
//    @Provides
//    @Singleton
//    fun provideAssetAPIService(okHttpClient: OkHttpClient): AssetAppService =
//        Retrofit.Builder()
//            .baseUrl(BuildConfig.assetBaseUrl)
//            .addConverterFactory(ResponseConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(okHttpClient)
//            .build()
//            .create(AssetAppService::class.java)
//
//    @Provides
//    @Singleton
//    fun provideOTCAPIService(okHttpClient: OkHttpClient): OTCAppService =
//        Retrofit.Builder()
//            .baseUrl(BuildConfig.otcBaseUrl)
//            .addConverterFactory(ResponseConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(okHttpClient)
//            .build()
//            .create(OTCAppService::class.java)
//
//    @Provides
//    @Singleton
//    fun provideUploadImageService(okHttpClient: OkHttpClient): UploadImageService =
//        Retrofit.Builder()
//            .baseUrl(BuildConfig.uploadImageBaseUrl)
//            .addConverterFactory(ResponseConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(okHttpClient)
//            .build()
//            .create(UploadImageService::class.java)
}