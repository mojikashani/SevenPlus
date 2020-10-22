package com.moji.sevenchallenge.models.response

import com.google.gson.annotations.SerializedName
import com.moji.sevenchallenge.models.Genre

data class MovieDetailsResponse(

    @SerializedName("id")
    var id: Int?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("genres")
    var genres: List<Genre>?,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName("runtime")
    var runtime: Int?,

    @SerializedName("vote_average")
    var voteAverage: Float?,
)