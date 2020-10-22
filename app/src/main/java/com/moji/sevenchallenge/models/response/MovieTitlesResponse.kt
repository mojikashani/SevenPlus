package com.moji.sevenchallenge.models.response

import com.google.gson.annotations.SerializedName
import com.moji.sevenchallenge.models.MovieTitle

data class MovieTitlesResponse(
    @SerializedName("page")
    var page: Int?,

    @SerializedName("total_pages")
    var totalPages: Int?,

    @SerializedName("results")
    var movieTitles: MutableList<MovieTitle>?
)