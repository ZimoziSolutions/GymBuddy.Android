package com.zimozi.assessment.ui.sample.fragment

import androidx.databinding.ViewDataBinding
import com.zimozi.assessment.R
import com.zimozi.assessment.base.BaseFragment
import com.zimozi.assessment.base.BaseViewModel
import com.zimozi.assessment.di.injector.Injectable

class SampleFragment : BaseFragment<ViewDataBinding>(),
    Injectable {
    override val setViewModel: BaseViewModel?
        get() = null

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun setDataBinding(): Boolean = false

    override fun initView() {

        // sampleFragmentViewModel.
    }
}