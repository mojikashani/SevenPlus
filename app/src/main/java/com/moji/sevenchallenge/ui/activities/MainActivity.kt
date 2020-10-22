package com.moji.sevenchallenge.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.moji.sevenchallenge.R
import com.moji.sevenchallenge.helpers.LoaderHelper
import com.moji.sevenchallenge.models.Category
import com.moji.sevenchallenge.models.MovieTitle
import com.moji.sevenchallenge.models.response.MovieTitlesResponse
import com.moji.sevenchallenge.presenters.MovieTitlesPresenter
import com.moji.sevenchallenge.ui.activities.base.BaseActivity
import com.moji.sevenchallenge.ui.activities.base.TransitionType
import com.moji.sevenchallenge.ui.adapters.CategoryAdapter
import com.moji.sevenchallenge.views.MovieTitlesView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loader_layout.*

class MainActivity : BaseActivity(), MovieTitlesView {

    private val categories = listOf(
        Category(0, "now_playing", "Now Playing"),
        Category(1,"popular", "Popular Movies"),
        Category(2, "top_rated", "Top Rated Movies"),
        Category(3, "upcoming", "Upcoming Movies")
    )

    private val presenter by lazy{ MovieTitlesPresenter(this) }

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loaderHelper = LoaderHelper(handler, loaderLayout)

        setupAdapter()

        reloadTitles()

        swipeRefreshLayout?.setOnRefreshListener {
            reloadTitles()
        }
    }

    private fun reloadTitles(){
        for(category in categories) {
            presenter.getMovieTitles(category.name)
        }
    }

    private fun setupAdapter(){
        categoryAdapter = CategoryAdapter(categories, object : CategoryAdapter.ItemClickListener{
            override fun onItemClick(movieTitle: MovieTitle) {
                Intent(this@MainActivity, MovieDetailsActivity::class.java).apply {
                    putExtra(MovieDetailsActivity.INTENT_MOVIE_ID, movieTitle.id)
                    startActivity(this, TransitionType.SLIDE)
                }

            }
        })
        recyclerCategories?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    override fun onTitlesFetched(movieTitlesResponse: MovieTitlesResponse, categoryName: String) {
        categories.findLast{it.name == categoryName}
            ?.also { category ->
                category.movieTitles = movieTitlesResponse.movieTitles ?: listOf()
                categoryAdapter.notifyItemChanged(category.index)
            }
    }

    override fun onNoNetworkError() {
        showErrorSnackBar(getString(R.string.error_no_internet_connection), hasTryAgain = true){
            reloadTitles()
        }
    }

    override fun hideLoading() {
        super<BaseActivity>.hideLoading()
        if(swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = false
        }
    }
}