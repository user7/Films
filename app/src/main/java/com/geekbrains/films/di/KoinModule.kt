package com.geekbrains.films.di

import com.geekbrains.films.model.repository.FilmRepository
import com.geekbrains.films.model.repository.transport.HttpTransport
import com.geekbrains.films.model.repository.transport.ThreadedHttpTransport
import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FilmsViewModel(get()) }
    single { FilmRepository(get()) }
    single<HttpTransport> { ThreadedHttpTransport() }
}