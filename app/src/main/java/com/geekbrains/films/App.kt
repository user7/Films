package com.geekbrains.films

import android.app.Application
import com.geekbrains.films.di.appModule
import com.geekbrains.films.model.db.FilmDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    private var db: FilmDatabase? = null

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    companion object {
        private var appInstance: App? = null
    }
}