package com.moji.sevenchallenge.ui.activities

import android.os.Bundle
import android.view.View
import com.moji.sevenchallenge.BuildConfig
import com.moji.sevenchallenge.R
import com.moji.sevenchallenge.helpers.LoaderHelper
import com.moji.sevenchallenge.models.response.MovieDetailsResponse
import com.moji.sevenchallenge.presenters.MovieDetailsPresenter
import com.moji.sevenchallenge.ui.activities.base.BaseActivity
import com.moji.sevenchallenge.ui.activities.base.TransitionType
import com.moji.sevenchallenge.views.MovieDetailsView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.loader_layout.*

class MovieDetailsActivity : BaseActivity(), MovieDetailsView {
    companion object{
        const val INTENT_MOVIE_ID = "INTENT_MOVIE_ID"
    }

    private val presenter by lazy{ MovieDetailsPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loaderHelper = LoaderHelper(handler, loaderLayout)

        val id = intent.getIntExtra(INTENT_MOVIE_ID, 0)
        presenter.getMovieDetails(id)
    }

    override fun onMovieDetailsFetched(movieDetails: MovieDetailsResponse) {
        // poster
        movieDetails.posterPath?.let { posterId ->
            val posterUrl = BuildConfig.THUMBNAIL_URL_LARGE + posterId
            Picasso.get().load(posterUrl).into(imagePoster)
        }

        // title
        txtTitle.text = movieDetails.title
        supportActionBar?.title = movieDetails.title

        // genre
        var genresList = ""
        for(genre in movieDetails.genres ?: listOf()){
            if(genresList.isNotEmpty())genresList+=", "
            genresList += genre.name
        }
        if(genresList.isNotEmpty()) {
            txtGenre.text = getString(R.string.genres_list, genresList)
        }else{
            txtGenre.visibility = View.GONE
        }

        // running time
        movieDetails.runtime?.let {
            txtRunningTime.text = getString(R.string.running_time, it.toString())
        }?: run{
            txtRunningTime.visibility = View.GONE
        }

        // average vote
        movieDetails.voteAverage?.let{
            txtAverageRate.text = getString(R.string.average_rate, it.toString())
        }?: run{
            txtAverageRate.visibility = View.GONE
        }

    }

    override fun onNoNetworkError() {
        showErrorSnackBar(getString(R.string.error_no_internet_connection), hasTryAgain = true){
            val id = intent.getIntExtra(INTENT_MOVIE_ID, 0)
            presenter.getMovieDetails(id)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish(TransitionType.SLIDE)
        return true
    }

    override fun onBackPressed() {
        finish(TransitionType.SLIDE)
    }
}