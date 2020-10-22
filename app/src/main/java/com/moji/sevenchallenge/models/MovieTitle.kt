package com.moji.sevenchallenge.models

import com.google.gson.annotations.SerializedName

data class MovieTitle(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("poster_path")
    var posterPath: String?,
)