package com.zimozi.assessment.di.component


import com.zimozi.assessment.di.module.ActivityBuilderModule
import com.zimozi.assessment.AssessmentApplication
import com.zimozi.assessment.di.AppModule
import com.zimozi.assessment.di.ServiceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, ServiceModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class]
)



interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: AssessmentApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: AssessmentApplication)
}
