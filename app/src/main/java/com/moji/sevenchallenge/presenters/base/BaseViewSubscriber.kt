package com.moji.sevenchallenge.presenters.base


import com.moji.sevenchallenge.helpers.GsonHelper
import com.moji.sevenchallenge.models.response.ErrorResponse
import com.moji.sevenchallenge.views.base.RequestView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception


abstract class BaseViewSubscriber<T : Any, V : RequestView>(
    private val view: V,
    private val isStealth: Boolean = false,
    progressMessage: String = LOADING) : Observer<T> {


    init {
        if(!isStealth) {
            view.showLoading(progressMessage)
        }
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(response: T) {
        if(response is Response<*>){
            val code = response.code()
            when{
                code < 300 ->{
                    onSucceed(response)
                }
                code == 401 ->{
                    view.onAuthorizationError(HttpException(response))
                }
                else ->{
                    if(!isStealth) {
                        view.onError(getErrorMessage(response))
                    }
                }
            }
        }else {
            onSucceed(response)
        }

        if(!isStealth) {
            view.hideLoading()
        }

    }

    override fun onError(e: Throwable) {
        if (e is HttpException && e.code() == 401) {
            view.onAuthorizationError(e)
        }else {// otherwise onError will be called
            if(!isStealth) {
                view.onError(getErrorMessage(e))
            }
        }
        if(!isStealth) {
            view.hideLoading()
        }
        e.printStackTrace()
    }

    override fun onComplete() {
    }

    protected abstract fun onSucceed(response: T)



    companion object{
        private const val DEFAULT_ERROR = "Something went wrong! please try again."
        private const val LOADING = "Loading..."
        fun getErrorMessage(e: Throwable): String{
            return if(e is HttpException){
                try {
                    val json = e.response().errorBody()?.string() ?: "{}"
                    val error = GsonHelper.fromJson(json, ErrorResponse::class.java)
                    error?.statusMessage ?: DEFAULT_ERROR
                }catch (ex: Exception){
                    DEFAULT_ERROR
                }
            }else{
                e.message ?: DEFAULT_ERROR
            }
        }

        fun getErrorMessage(e: Response<*>): String{
            return try {
                val json = e.errorBody()?.string() ?: "{}"
                val error = GsonHelper.fromJson(json , ErrorResponse::class.java)
                error?.statusMessage ?: DEFAULT_ERROR
            }catch (ex: Exception){
                DEFAULT_ERROR
            }
        }
    }

}
