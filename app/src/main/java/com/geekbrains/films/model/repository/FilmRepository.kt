package com.geekbrains.films.model.repository

import android.graphics.Bitmap
import com.geekbrains.films.model.repository.transport.rest.FilmSearchResultDTO

interface FilmRepository {
    companion object {
        const val TMDB_KEY3 = "80741e82e9f539526693f267ad1f5b10"
    }

    fun findFilms(words: String, callback: (FilmSearchResultDTO) -> Unit)
    fun fetchImage(path: String, callback: (Bitmap) -> Unit)
}