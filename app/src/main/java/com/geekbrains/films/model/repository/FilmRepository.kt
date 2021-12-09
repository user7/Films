package com.geekbrains.films.model.repository

import android.graphics.Bitmap
import com.geekbrains.films.BuildConfig
import com.geekbrains.films.model.repository.transport.rest.FilmSearchResultDTO

interface FilmRepository {
    companion object {
        val TMDB_KEY3: String = BuildConfig.TMDB_KEY3
    }

    fun findFilms(query: String, callback: (FilmSearchResultDTO) -> Unit)
    fun fetchImage(path: String, callback: (Bitmap) -> Unit)
}