package com.zimozi.assessment.util

import androidx.lifecycle.MutableLiveData
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class RxUtil {
    companion object {
        fun <T> applySchedulersRx2(compositeDisposable: CompositeDisposable?): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream ->
                upstream
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { compositeDisposable?.add(it) }
            }
        }

        fun <T> applySchedulersRx2(loadingLiveData: MutableLiveData<Boolean>?, compositeDisposable: CompositeDisposable?): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream ->
                upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { disposable ->
                        compositeDisposable?.add(disposable)
                        loadingLiveData?.value = true
                    }
                    .doFinally { loadingLiveData?.value = false }

            }
        }
    }
}
