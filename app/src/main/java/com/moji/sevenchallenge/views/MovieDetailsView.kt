package com.moji.sevenchallenge.views

import com.moji.sevenchallenge.models.response.MovieDetailsResponse
import com.moji.sevenchallenge.views.base.RequestView

interface MovieDetailsView : RequestView {
    fun onMovieDetailsFetched(movieDetails: MovieDetailsResponse)
}