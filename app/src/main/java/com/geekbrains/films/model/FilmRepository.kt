package com.geekbrains.films.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.geekbrains.films.d
import com.geekbrains.films.model.rest.FilmSearchResultDTO
import com.geekbrains.films.view.MainActivity
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.lang.Exception
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object FilmRepository {
    const val KEY3 = "80741e82e9f539526693f267ad1f5b10"

    fun findFilms(words: String, callback: (FilmSearchResultDTO) -> Unit) {
        val q = URLEncoder.encode(words, "UTF-8")
        HttpClient.queryText(
            "https://api.themoviedb.org/3/search/movie?api_key=$KEY3&query=$q",
            { json -> callback(Gson().fromJson(json, FilmSearchResultDTO::class.java)) },
            { error -> d("search query error: $error") }
        )
    }

    fun fetchImage(path: String, callback: (Bitmap) -> Unit) {
        HttpClient.queryBytes(
            "https://image.tmdb.org/t/p/w300$path",
            { bytes -> callback(BitmapFactory.decodeStream(ByteArrayInputStream(bytes))) },
            { error -> d("image query error: $error") }
        )
    }
}