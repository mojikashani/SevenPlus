package com.moji.sevenchallenge.injection

import android.app.Application
import android.content.Context
import com.moji.sevenchallenge.injection.component.AppComponent
import com.moji.sevenchallenge.injection.component.DaggerAppComponent
import com.moji.sevenchallenge.injection.module.AppModule

class MainApplication: Application() {

    companion object{
        lateinit var appContext: Context
    }

    private val component: AppComponent by lazy {
        AppModule.app = this
        DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        component.inject(this)
    }
}