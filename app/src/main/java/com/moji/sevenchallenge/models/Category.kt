package com.moji.sevenchallenge.models

import com.moji.sevenchallenge.models.response.MovieTitlesResponse

data class Category(
    val index: Int,
    val name: String,
    val displayName: String,
    var movieTitlesResponse: MovieTitlesResponse = MovieTitlesResponse(1, 1, mutableListOf())
)