package com.zimozi.assessment.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zimozi.assessment.di.ViewModelFactory
import com.zimozi.assessment.di.ViewModelKey
import com.zimozi.assessment.ui.sample.activity.DashboardActivityViewModel


import com.zimozi.assessment.ui.sample.activity.SampleActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    //
    @Binds
    @IntoMap
    @ViewModelKey(SampleActivityViewModel::class)
    abstract fun bindSampleActivityViewModel(viewModel: SampleActivityViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(DashboardActivityViewModel::class)
    abstract fun bindDashboardActivityViewModel(viewModel: DashboardActivityViewModel): ViewModel


}
