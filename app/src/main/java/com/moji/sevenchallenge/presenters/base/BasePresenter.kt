package com.moji.sevenchallenge.presenters.base

import android.content.Context
import com.moji.sevenchallenge.extensions.isNetworkAvailable
import com.moji.sevenchallenge.injection.component.DaggerPresenterComponent
import com.moji.sevenchallenge.injection.component.PresenterComponent
import com.moji.sevenchallenge.injection.module.AppModule
import com.moji.sevenchallenge.injection.module.NetworkModule
import com.moji.sevenchallenge.network.SevenApi
import com.moji.sevenchallenge.presenters.MovieDetailsPresenter
import com.moji.sevenchallenge.presenters.MovieTitlesPresenter
import com.moji.sevenchallenge.views.base.RequestView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 * @param V the type of the View the presenter is based on
 * @property view the view the presenter is based on
 * @constructor Injects the required dependencies
 */
abstract class BasePresenter<out V : RequestView>(protected val view: V) {

    init {
        val injector = DaggerPresenterComponent
            .builder()
            .appModule(AppModule())
            .networkModule(NetworkModule())
            .build()
        inject(injector)
    }

    @Inject
    protected lateinit var context : Context

    @Inject
    protected lateinit var sevenApi: SevenApi

    // Set this flag to false for synchronous execution, when in testing
    var runASynchronous = true


    protected fun <T> callApi(baseObservable : Observable<T>, observer: Observer<T>){
        if (context.isNetworkAvailable) {
            // run synchronised if flag is false
            var observable  = baseObservable
            if (runASynchronous) {
                observable = baseObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
            }
            observable.subscribe(observer)
        } else {
            view.onNoNetworkError()
            view.hideLoading()
        }
    }

    protected fun <T> callCompletable(baseObservable : Observable<T>, observer: Observer<T>){
        if (context.isNetworkAvailable) {
            // run synchronised if flag is false
            var observable  = baseObservable
            if (runASynchronous) {
                observable = baseObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
            }
            observable.subscribe(observer)
        } else {
            view.onNoNetworkError()
            view.hideLoading()
        }
    }

    private fun inject(injector: PresenterComponent) {
        when (this) {
            is MovieTitlesPresenter -> injector.inject(this)
            is MovieDetailsPresenter -> injector.inject(this)
        }
    }

}