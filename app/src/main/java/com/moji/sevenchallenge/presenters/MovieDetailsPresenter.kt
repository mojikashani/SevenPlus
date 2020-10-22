package com.moji.sevenchallenge.presenters

import com.moji.sevenchallenge.BuildConfig
import com.moji.sevenchallenge.models.response.MovieDetailsResponse
import com.moji.sevenchallenge.presenters.base.BasePresenter
import com.moji.sevenchallenge.presenters.base.BaseViewSubscriber
import com.moji.sevenchallenge.views.MovieDetailsView

class MovieDetailsPresenter(view: MovieDetailsView)
    : BasePresenter<MovieDetailsView>(view) {

    fun getMovieDetails(id: Int) {
        callApi(sevenApi.getMovieDetails(id, BuildConfig.API_KEY), MovieDetailsObserver(view))
    }

    private inner class MovieDetailsObserver(val _view: MovieDetailsView) : BaseViewSubscriber<MovieDetailsResponse, MovieDetailsView>(_view) {
        override fun onSucceed(response: MovieDetailsResponse) {
            _view.onMovieDetailsFetched(response)
        }
    }

}