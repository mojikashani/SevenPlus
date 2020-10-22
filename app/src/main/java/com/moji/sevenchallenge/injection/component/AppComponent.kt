package com.moji.sevenchallenge.injection.component

import android.app.Application
import com.moji.sevenchallenge.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: Application)
}