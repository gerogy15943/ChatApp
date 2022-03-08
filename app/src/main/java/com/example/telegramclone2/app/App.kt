package com.example.telegramclone2.app

import android.app.Application
import com.example.telegramclone2.di.AppComponent
import com.example.telegramclone2.di.AppModule
import com.example.telegramclone2.di.DaggerAppComponent


class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}
