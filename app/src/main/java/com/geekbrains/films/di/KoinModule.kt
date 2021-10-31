package com.geekbrains.films.di

import com.geekbrains.films.model.repository.FilmRepository
import com.geekbrains.films.model.repository.threaded.ThreadedFilmRepository
import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FilmsViewModel(get()) }
    single<FilmRepository> { ThreadedFilmRepository() }
}