package com.zimozi.assessment.base

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.zimozi.assessment.R
import com.zimozi.assessment.data.model.GymDataResponse
import com.zimozi.assessment.data.preferences.PreferencesHelper
import com.zimozi.assessment.di.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector

import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity(),
    HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    lateinit var baseAction: BaseAction

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected inline fun <reified VM : BaseViewModel> ViewModelProvider() = lazy {
        ViewModelProviders.of(this, viewModelFactory).get(VM::class.java)
    }

    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutId) as DB
    }

    abstract val layoutId: Int

    abstract val setViewModel: BaseViewModel?

    /**
     * Return is use data binding on activity and set viewmodel
     */
    abstract fun setDataBinding(): Boolean

    abstract fun initView()

    abstract fun detachView()


    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AndroidInjection.inject(this)
        if (setDataBinding()) {
            binding.lifecycleOwner = this
            binding.executePendingBindings()
        } else {
            val coordinatorLayout: CoordinatorLayout =
                layoutInflater.inflate(R.layout.activity_base, null) as CoordinatorLayout
            val activityContainer: FrameLayout =
                coordinatorLayout.findViewById(R.id.layout_container)
            layoutInflater.inflate(layoutId, activityContainer, true)
            setContentView(coordinatorLayout)
            //setContentView(layoutId)
        }
        initBaseAction()
        setStatusBar()
        //disableMenu()
        initView()
        setBackButton()
    }

    private fun initBaseAction() {
        baseAction = BaseAction(this).apply {
            initObserverViewModel(this@BaseActivity, setViewModel)

        }
    }

    fun showLoading() {
        baseAction.showLoading()
    }

    fun hideLoading() {
        baseAction.hideLoading()
    }

    open fun setBackButton(func: (() -> Unit)? = null) {
//        btn_back?.setOnClickListener {
//            if (func != null) {
//                func.invoke()
//            } else {
//                onBackPressed()
//            }
//        }
    }


    fun getUserData(): GymDataResponse? {
        val json = preferencesHelper!!.getStringNew("userData")
        return Gson().fromJson(json, GymDataResponse::class.java)
    }


    fun saveUserData(userData: GymDataResponse) {
        val json = Gson().toJson(userData)
        preferencesHelper!!.putStringNew("userData", json)
    }

    fun setTitleHeader(title: String) {
        //tv_title?.text = title
    }

    fun setTitleHeader(resId: Int) {
        //tv_title?.text = getString(resId)
    }

    /*open fun setStatusBar() {
        // StatusBarUtil.setDarkMode(this)
        // StatusBarUtil.setTransparent(this)
        StatusBarUtil.setTransparentForImageView(this, null)
    }*/
    open fun setStatusBar() {

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        // StatusBarUtil.setDarkMode(this)
        // StatusBarUtil.setTransparent(this)
        // StatusBarUtil.setTransparentForImageView(this, null)
    }


    fun setTransparentStatusBar() {
        // set fit system window false
        val parent = findViewById<ViewGroup>(android.R.id.content)
        var i = 0
        val count = parent.childCount
        while (i < count) {
            val childView = parent.getChildAt(i)
            if (childView is ViewGroup) {
                childView.setFitsSystemWindows(false)
                childView.clipToPadding = false
            }
            i++
        }
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
//            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    fun hideToolbar() {
        container_toolbar?.visibility = View.GONE
    }

    fun showToolbar() {
        container_toolbar?.visibility = View.VISIBLE
    }

    fun addFragment(savedInstanceState: Bundle?, fragment: Fragment) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.layout_container, fragment)
                .commit()
        }
    }

    fun addFragmentToStack(savedInstanceState: Bundle?, fragment: Fragment) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.layout_container, fragment)
                .addToBackStack(fragment.tag)
                .commit()
        }
    }

    fun replaceFragment(savedInstanceState: Bundle?, fragment: Fragment, tag: String? = "") {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.layout_container, fragment, tag)
                .commit()
        }
    }

  /*  override fun onBackPressed() {
        if (!popFragment()) {
            super.onBackPressed()
        }
    }

    fun popFragment(): Boolean {
        var isPop = false
        try {
            if (supportFragmentManager.backStackEntryCount > 0) {
                val tag =
                    supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name
                val fragment = supportFragmentManager.findFragmentByTag(tag)
                if (fragment is BaseFragment<*> && fragment.onBackPressed()) {
                    isPop = true
                } else {
                    isPop = true
                    supportFragmentManager.popBackStack()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isPop
    }*/

//    fun addMenuFragment(savedInstanceState: Bundle?, fragment: Fragment, tag: String? = "") {
//        if (savedInstanceState == null) {
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.menu_container, fragment, tag)
//                .commit()
//        }
//    }

//    fun enableMenu() {
//        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//    }
//
//    fun disableMenu() {
//        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//    }
//
//    fun closeMenu() {
//        drawer_layout?.closeDrawer(Gravity.LEFT)
//    }
//
//    fun openMenu() {
//        drawer_layout?.openDrawer(Gravity.LEFT)
//    }

    override fun onDestroy() {
        super.onDestroy()
        detachView()
    }

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

//    override fun attachBaseContext(newBase: Context) {
//        super.attachBaseContext(ViewPumpContextWrapper.wrap(LocaleManager.setLocal(newBase)))
//    }

    fun startActivity(clz: Class<*>, flags: Int? = null) {
        baseAction.startActivity(clz, flags)
    }

    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        baseAction.startActivity(clz, bundle)
    }

    fun startActivity(clz: Class<*>, animation: Boolean = true) {
        startActivity(Intent(this@BaseActivity, clz))
        if (animation) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun startActivity(clz: Class<*>, bundle: Bundle?, animation: Boolean = true) {
        val intent = Intent()
        intent.setClass(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (animation) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun startActivityForResult(
        cls: Class<*>, bundle: Bundle?,
        requestCode: Int
    ) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }
}