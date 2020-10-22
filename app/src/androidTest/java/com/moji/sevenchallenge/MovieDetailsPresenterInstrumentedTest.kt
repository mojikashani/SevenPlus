package com.moji.sevenchallenge

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.moji.sevenchallenge.extensions.mockNetworkAvailabilityToFalse
import com.moji.sevenchallenge.models.response.MovieDetailsResponse
import com.moji.sevenchallenge.presenters.MovieDetailsPresenter
import com.moji.sevenchallenge.tools.*
import com.moji.sevenchallenge.views.MovieDetailsView
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.capture
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*

@RunWith(AndroidJUnit4::class)
internal class MovieDetailsPresenterInstrumentedTest {
    @Mock
    private val mMockMovieDetailsView: MovieDetailsView = Mockito.mock(MovieDetailsView::class.java)
    @Captor
    private lateinit var captor: ArgumentCaptor<MovieDetailsResponse>
    private lateinit var appContext : Context

    private lateinit var presenter : MovieDetailsPresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        presenter = MovieDetailsPresenter(mMockMovieDetailsView)
        presenter.runASynchronous = false
    }

    @Test
    fun presenter_returns_at_least_one_movie_title() {
        presenter.getMovieDetails(SAMPLE_MOVIE_ID)
        Mockito.verify(mMockMovieDetailsView).showLoading(Mockito.anyString())
        Mockito.verify(mMockMovieDetailsView).onMovieDetailsFetched(capture(captor))
        Mockito.verify(mMockMovieDetailsView).hideLoading()
        val capturedArgument = captor.value
        Assert.assertThat(capturedArgument.title, CoreMatchers.equalTo(SAMPLE_MOVIE_TITLE))
    }

    @Test
    fun presenter_returns_error_on_wrong_category() {
        presenter.getMovieDetails(WRONG_MOVIE_ID)
        Mockito.verify(mMockMovieDetailsView).showLoading(Mockito.anyString())
        Mockito.verify(mMockMovieDetailsView).onError(any())
        Mockito.verify(mMockMovieDetailsView).hideLoading()
    }

    @Test
    fun presenter_returns_no_network_error() {
        mockNetworkAvailabilityToFalse = true

        presenter.getMovieDetails(WRONG_MOVIE_ID)
        Mockito.verify(mMockMovieDetailsView).showLoading(Mockito.anyString())
        Mockito.verify(mMockMovieDetailsView).onNoNetworkError()
        Mockito.verify(mMockMovieDetailsView).hideLoading()

        mockNetworkAvailabilityToFalse = false
    }
}