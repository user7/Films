package com.geekbrains.films.di

import com.geekbrains.films.model.db.FilmDatabaseHolder
import com.geekbrains.films.model.repository.FilmRepository
import com.geekbrains.films.model.repository.FilmRepositoryHttp
import com.geekbrains.films.model.repository.transport.HttpTransport
import com.geekbrains.films.model.repository.transport.OkHttpTransport
import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FilmsViewModel(get()) }
    single<FilmRepository> { FilmRepositoryHttp(get()) }

    // single<HttpTransport> { BroadcastServiceHttpTransport(get()) }
    // single<HttpTransport> { ThreadedHttpTransport() }
    single<HttpTransport> { OkHttpTransport() }

    single<FilmDatabaseHolder> { FilmDatabaseHolder(androidContext()) }
}