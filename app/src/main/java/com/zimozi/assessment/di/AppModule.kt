package com.zimozi.assessment.di

import android.content.Context
import android.content.SharedPreferences

import com.zimozi.assessment.AssessmentApplication
import com.zimozi.assessment.di.module.RemoteModule
import com.zimozi.assessment.di.module.ViewModelModule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, RemoteModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: AssessmentApplication): Context = application

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create()

//    @Provides
//    @Singleton
//    fun providePublicInfo(symbolManager: SymbolManager, gson: Gson): PublicInfoManager {
//        return PublicInfoManager(symbolManager, gson)
//    }



    @Provides
    @Singleton
    fun provideSharedPreference(context: Context):
            SharedPreferences {
        return context.getSharedPreferences("NeedApp", Context.MODE_PRIVATE)
    }

}

@Module
internal abstract class ServiceModule {

//    @ContributesAndroidInjector
//    internal abstract fun bindCustomNotificationExtenderService(): CustomNotificationExtenderService

}