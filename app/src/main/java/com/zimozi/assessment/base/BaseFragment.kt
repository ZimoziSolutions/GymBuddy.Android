package com.zimozi.assessment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.zimozi.assessment.di.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragment<VDB : ViewDataBinding> : Fragment() {


    var baseActivity: BaseActivity<ViewDataBinding>? = null
    abstract val layoutId: Int


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected inline fun <reified VM : BaseViewModel> ViewModelProvider() = lazy {
        ViewModelProviders.of(this, viewModelFactory).get(VM::class.java)
    }

    protected inline fun <reified VM : BaseViewModel> ViewModelProviderFromParent() = lazy {
        ViewModelProviders.of(this.parentFragment!!, viewModelFactory).get(VM::class.java)
    }

    protected inline fun <reified VM : BaseViewModel> ViewModelProviderFromActivity() = lazy {
        ViewModelProviders.of(requireActivity(), viewModelFactory).get(VM::class.java)
    }

    lateinit var binding: VDB

    lateinit var baseAction: BaseAction

    abstract val setViewModel: BaseViewModel?

    abstract fun setDataBinding(): Boolean

    protected abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (setDataBinding()) {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            binding.lifecycleOwner = this
            return binding.root
        } else {
            return inflater.inflate(layoutId, container, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseActivity = activity as BaseActivity<ViewDataBinding>?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseAction()
        initView()
    }

    private fun initBaseAction() {
        context?.let {
            baseAction = BaseAction(it).apply {
                initObserverViewModel(this@BaseFragment, setViewModel)
            }
        }
//        context?.let {
//
//        tokenAuthenticator = TokenAuthenticator(it).apply {
//            initObserverViewModel(this@BaseFragment, setViewModel)
//
//        }
//        }
    }
}