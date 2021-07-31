package com.zimozi.assessment.di.module

import com.zimozi.assessment.di.module.fragmentbuilder.FragmentBuilderModule
import com.zimozi.assessment.ui.sample.activity.DashboardActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

import com.zimozi.assessment.ui.sample.activity.SampleActivity


@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun SampleActivity(): SampleActivity

//    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
//    abstract fun HomeActivity(): HomeActivity


    @ContributesAndroidInjector()
    abstract fun DashboardActivity(): DashboardActivity




}