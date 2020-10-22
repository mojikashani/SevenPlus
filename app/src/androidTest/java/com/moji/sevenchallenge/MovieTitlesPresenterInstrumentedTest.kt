package com.moji.sevenchallenge

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.moji.sevenchallenge.extensions.mockNetworkAvailabilityToFalse
import com.moji.sevenchallenge.models.MovieTitle
import com.moji.sevenchallenge.models.response.MovieTitlesResponse
import com.moji.sevenchallenge.presenters.MovieTitlesPresenter
import com.moji.sevenchallenge.tools.POPULAR_CATEGORY
import com.moji.sevenchallenge.tools.WRONG_CATEGORY
import com.moji.sevenchallenge.views.MovieTitlesView
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.capture
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*

@RunWith(AndroidJUnit4::class)
internal class MovieTitlesPresenterInstrumentedTest {
    @Mock
    private val mMockMovieTitlesView: MovieTitlesView = Mockito.mock(MovieTitlesView::class.java)
    @Captor
    private lateinit var captor: ArgumentCaptor<MovieTitlesResponse>
    private lateinit var appContext : Context

    private lateinit var presenter : MovieTitlesPresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        presenter = MovieTitlesPresenter(mMockMovieTitlesView)
        presenter.runASynchronous = false
    }

    @Test
    fun presenter_returns_at_least_one_movie_title() {
        presenter.getMovieTitles(POPULAR_CATEGORY)
        Mockito.verify(mMockMovieTitlesView).showLoading(Mockito.anyString())
        Mockito.verify(mMockMovieTitlesView).onTitlesFetched(capture(captor), any())
        Mockito.verify(mMockMovieTitlesView).hideLoading()
        val capturedArgument = captor.value
        Assert.assertThat(capturedArgument.movieTitles, CoreMatchers.not(emptyList()))
    }

    @Test
    fun presenter_returns_error_on_wrong_category() {
        presenter.getMovieTitles(WRONG_CATEGORY)
        Mockito.verify(mMockMovieTitlesView).showLoading(Mockito.anyString())
        Mockito.verify(mMockMovieTitlesView).onError(any())
        Mockito.verify(mMockMovieTitlesView).hideLoading()
    }

    @Test
    fun presenter_returns_no_network_error() {
        mockNetworkAvailabilityToFalse = true

        presenter.getMovieTitles(POPULAR_CATEGORY)
        Mockito.verify(mMockMovieTitlesView).showLoading(Mockito.anyString())
        Mockito.verify(mMockMovieTitlesView).onNoNetworkError()
        Mockito.verify(mMockMovieTitlesView).hideLoading()

        mockNetworkAvailabilityToFalse = false
    }

}