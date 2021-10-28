package com.geekbrains.films.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.geekbrains.films.d
import com.geekbrains.films.model.rest.FilmSearchResultDTO
import com.geekbrains.films.services.UrlFetchService
import com.geekbrains.films.view.MainActivity
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.lang.Exception
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object FilmRepository {
    const val KEY3 = "80741e82e9f539526693f267ad1f5b10"

    fun findFilms(words: String) {
        val q = URLEncoder.encode(words, "UTF-8")
        val url = "https://api.themoviedb.org/3/search/movie?api_key=$KEY3&query=$q"
        UrlFetchService.start(url)
    }

    fun fetchImage(path: String) {
        val url = "https://image.tmdb.org/t/p/w300$path"
        UrlFetchService.start(url)
    }
}