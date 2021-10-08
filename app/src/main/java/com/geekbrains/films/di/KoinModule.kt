package com.geekbrains.films.di

import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FilmsViewModel() }
}