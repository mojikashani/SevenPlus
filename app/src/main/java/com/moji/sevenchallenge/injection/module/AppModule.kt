package com.moji.sevenchallenge.injection.module

import android.content.Context
import com.moji.sevenchallenge.injection.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module
class AppModule {

    companion object {
        lateinit var app: MainApplication
    }
    @Provides
    @Singleton
    fun provideApplication() = app

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return app.applicationContext
    }
}