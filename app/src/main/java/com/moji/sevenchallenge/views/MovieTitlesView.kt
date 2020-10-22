package com.moji.sevenchallenge.views

import com.moji.sevenchallenge.models.response.MovieTitlesResponse
import com.moji.sevenchallenge.views.base.RequestView

interface MovieTitlesView : RequestView {
    fun onTitlesFetched(movieTitlesResponse: MovieTitlesResponse, categoryName: String)
}