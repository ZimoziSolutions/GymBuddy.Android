package com.zimozi.assessment.di.module.fragmentbuilder

import com.zimozi.assessment.ui.sample.fragment.SampleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeSampleFragment(): SampleFragment



}