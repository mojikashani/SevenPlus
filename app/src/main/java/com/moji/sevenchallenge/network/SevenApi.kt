package com.moji.sevenchallenge.network

import com.moji.sevenchallenge.models.response.MovieDetailsResponse
import com.moji.sevenchallenge.models.response.MovieTitlesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SevenApi{

    @GET("movie/{category}")
    fun getMovieTitles(@Path("category") category: String
                       , @Query("api_key") apiKey: String, @Query("page") page: Int): Observable<MovieTitlesResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int
                        , @Query("api_key") apiKey: String): Observable<MovieDetailsResponse>
}