package com.moji.sevenchallenge.injection.component


import com.moji.sevenchallenge.injection.module.AppModule
import com.moji.sevenchallenge.injection.module.NetworkModule
import com.moji.sevenchallenge.presenters.MovieDetailsPresenter
import com.moji.sevenchallenge.presenters.MovieTitlesPresenter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class)])
interface PresenterComponent {

    fun inject(presenter: MovieTitlesPresenter)
    fun inject(presenter: MovieDetailsPresenter)

}