package com.moji.sevenchallenge.models

data class Category(
    val index: Int,
    val name: String,
    val displayName: String,
    var movieTitles: List<MovieTitle> = listOf()
)