package com.moji.sevenchallenge.presenters

import com.moji.sevenchallenge.BuildConfig
import com.moji.sevenchallenge.models.response.MovieTitlesResponse
import com.moji.sevenchallenge.presenters.base.BasePresenter
import com.moji.sevenchallenge.presenters.base.BaseViewSubscriber
import com.moji.sevenchallenge.views.MovieTitlesView

class MovieTitlesPresenter(view: MovieTitlesView)
    : BasePresenter<MovieTitlesView>(view) {

    fun getMovieTitles(category: String, page: Int = 1) {
        callApi(sevenApi.getMovieTitles(category, BuildConfig.API_KEY, page), MovieTitlesObserver(view, category))
    }

    private inner class MovieTitlesObserver(val _view: MovieTitlesView, val category: String) : BaseViewSubscriber<MovieTitlesResponse, MovieTitlesView>(_view) {
        override fun onSucceed(response: MovieTitlesResponse) {
            _view.onTitlesFetched(response, category)
        }
    }

}