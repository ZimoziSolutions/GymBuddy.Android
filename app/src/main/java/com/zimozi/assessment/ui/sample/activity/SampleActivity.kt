package com.zimozi.assessment.ui.sample.activity

import androidx.databinding.ViewDataBinding
import com.zimozi.assessment.R
import com.zimozi.assessment.base.BaseActivity
import com.zimozi.assessment.base.BaseViewModel

class SampleActivity : BaseActivity<ViewDataBinding>() {
    override val setViewModel: BaseViewModel?
        get() = null

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun detachView() {
    }

    override fun setDataBinding(): Boolean = false

    override fun initView() {

    }
}