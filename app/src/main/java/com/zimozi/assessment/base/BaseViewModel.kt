package com.zimozi.assessment.base

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zimozi.assessment.data.DataRepository
import com.zimozi.assessment.data.networking.*
import com.zimozi.assessment.data.preferences.PreferencesHelper
import com.zimozi.assessment.util.RxUtil
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var dataRepository: DataRepository
    private val compositeDisposable = CompositeDisposable()


    @Inject
    lateinit var preferencesHelper: PreferencesHelper

//    fun getUserInfoData(): UserInfoData? {
//        return dataRepository.loginManager.userInfoData
//    }
//
//    fun getPublicInfo(): PublicInfoManager {
//        return dataRepository.publicInfoManager
//    }
//
//    fun getCurrentCoinMap(): CoinMapBean {
//        return dataRepository.publicInfoManager.getCurrentCoinMap()
//    }

    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<ErrorCallBack>()

    fun logOut() {
        dataRepository.preferencesHelper.clear()
    }



    //
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
    //
//
    var publishSubject: PublishSubject<() -> Unit>? = null

    @SuppressLint("CheckResult")
    fun createDebounceRequest(func: (() -> Unit)) {
        if (publishSubject == null) {
            com.zimozi.assessment.util.LogUtil.i("Init debcoune")
            publishSubject = PublishSubject.create<() -> Unit>()
            publishSubject!!
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    com.zimozi.assessment.util.LogUtil.i("Init deboune")
                    it.invoke()
                    publishSubject = null
                }
            publishSubject!!.onNext(func)
        } else {
            publishSubject!!.onNext(func)
        }
    }

    /**
     * call manual chain observable
     */

    fun <T> Observable<T>.next(func: (T) -> Unit): Observable<T>? {
        return this.doOnNext { t -> func(t) }
    }

    @SuppressLint("CheckResult")
    fun <T> Observable<HttpResult<T>>.subscribeResult(
        responseAPI: ResponseAPI<T>,
        myOwnLoading: MutableLiveData<Boolean>? = null,
        isShowLoading: Boolean = true
    ) {
        Observable
            .create<String> {
                (myOwnLoading ?: if (isShowLoading) loadingState else null)?.value = true
                // if (FirebaseAuth.getInstance().currentUser != null) {
                //     FirebaseAuth.getInstance().currentUser?.getIdToken(true)
                //         ?.addOnCompleteListener { task ->
                //             if (task.isSuccessful) {
                //                 preferencesHelper.saveToken(task.result?.token)
                //             }
                //             it.onNext("")
                //         }
                // } else {
                //     it.onNext("")
                // }
                it.onNext("")
            }
            .flatMap {
                this
                    .compose(
                        RxUtil.applySchedulersRx2(
                            myOwnLoading ?: if (isShowLoading) loadingState else null,
                            compositeDisposable
                        )
                    )
            }
            .subscribe(object : CallbackWrapper<T>(errorState) {
                override fun onCompleteRequest() {
                    com.zimozi.assessment.util.LogUtil.i("onCompleteRequest")
                    (myOwnLoading ?: if (isShowLoading) loadingState else null)?.value = false
                }

                override fun onSuccess(data: T?) {
                    responseAPI.onResponseData(data)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun <T> requestSubAPI(observable: Observable<HttpResult<T>>, responseAPI: ResponseAPI<T>?) {
        observable
            .compose(RxUtil.applySchedulersRx2(null, compositeDisposable))
            .subscribe(object : CallbackWrapper<T>(errorState) {
                override fun onCompleteRequest() {
//                    loadingState?.value = false
                }

                override fun onSuccess(data: T?) {
                    responseAPI?.onResponseData(data)
                }
            })
    }

    /**
     * Call single chain
     */
    @SuppressLint("CheckResult")
    fun <T> requestAPI(
        observable: Observable<HttpResult<T>>,
        responseAPI: ResponseAPI<T>,
        myOwnLoading: MutableLiveData<Boolean>? = null,
        myOwnErrorState: MutableLiveData<ErrorCallBack>? = null,
        isShowLoading: Boolean = true
    ) {
        Observable
            .create<String> {
                (myOwnLoading ?: if (isShowLoading) loadingState else null)?.value = true
                it.onNext("")
            }
            .flatMap {
                observable.compose(
                    RxUtil.applySchedulersRx2(
                        myOwnLoading ?: if (isShowLoading) loadingState else null,
                        compositeDisposable
                    )
                )
            }
            .subscribe(object : CallbackWrapper<T>(myOwnErrorState ?: errorState) {
                override fun onCompleteRequest() {
                    com.zimozi.assessment.util.LogUtil.i("onCompleteRequest")
                    (myOwnLoading ?: if (isShowLoading) loadingState else null)?.value = false
                }

                override fun onSuccess(data: T?) {
                    responseAPI.onResponseData(data)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun <T> requestAPI1(
        observable: Observable<T>,
        responseAPI: ResponseAPI<T>,
        myOwnLoading: MutableLiveData<Boolean>? = null,
        myOwnErrorState: MutableLiveData<ErrorCallBack>? = null,
        isShowLoading: Boolean = true
    ) {
        Observable
            .create<String> {
                 if (FirebaseAuth.getInstance().currentUser != null) {
                     FirebaseAuth.getInstance().currentUser?.getIdToken(true)
                         ?.addOnCompleteListener { task ->
                             if (task.isSuccessful) {
                                 dataRepository.preferencesHelper.saveToken(task.result?.token)
                             }
                             it.onNext("")
                         }
                 } else {
                     it.onNext("")
                 }
                (myOwnLoading ?: if (isShowLoading) loadingState else null)?.value = true
              //  it.onNext("")
            }
            .flatMap {
                observable.compose(
                    RxUtil.applySchedulersRx2(
                        myOwnLoading ?: if (isShowLoading) loadingState else null,
                        compositeDisposable
                    )
                )
            }
            .subscribeWith(object : CallbackWrapper1<T>(myOwnErrorState ?: errorState) {
                override fun onSuccess(t: T) {
                    responseAPI.onResponseData(t)
                }

                override fun onCompleteRequest() {
                    com.zimozi.assessment.util.LogUtil.i("onCompleteRequest")
                    (myOwnLoading ?: if (isShowLoading) loadingState else null)?.value = false
                }


            })
    }

    /**
     * Call single chain
     */
    @SuppressLint("CheckResult")
    fun <T> requestEverestAPI(
        observable: Observable<T>,
        responseAPI: ResponseAPI<T>,
        myOwnLoading: MutableLiveData<Boolean>? = null,
        myOwnErrorState: MutableLiveData<ErrorCallBack>? = null,
        isShowLoading: Boolean = true
    ) {
        Observable
            .create<String> {
                (myOwnLoading ?: if (isShowLoading) loadingState else null)?.value = true
                it.onNext("")
            }
            .flatMap {
                observable.compose(
                    RxUtil.applySchedulersRx2(
                        myOwnLoading ?: if (isShowLoading) loadingState else null,
                        compositeDisposable
                    )
                )
            }
            .subscribe(object : EverestCallbackWrapper<T>(myOwnErrorState ?: errorState) {
                override fun onCompleteRequest() {
                    com.zimozi.assessment.util.LogUtil.i("onCompleteRequest")
                    (myOwnLoading ?: if (isShowLoading) loadingState else null)?.value = false
                }

                override fun onSuccess(data: T?) {
                    responseAPI.onResponseData(data)
                }
            })
    }

    @SuppressLint("CheckResult")
    fun <T> requestOnBackground(observable: Observable<T>) {
        observable
            .subscribeOn(Schedulers.io())
            .subscribe({}, {
                com.zimozi.assessment.util.LogUtil.e("Error when request background: $it.localizedMessage")
            })
    }

    interface ResponseAPI<T> {
        fun onResponseData(data: T?)
    }
}