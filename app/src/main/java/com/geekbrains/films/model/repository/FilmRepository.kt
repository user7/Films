package com.geekbrains.films.model.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.geekbrains.films.model.repository.transport.HttpTransport
import com.geekbrains.films.model.rest.FilmSearchResultDTO
import com.google.gson.Gson
import java.net.URLEncoder

class FilmRepository(val transport: HttpTransport) {
    companion object {
        const val TMDB_KEY3 = "80741e82e9f539526693f267ad1f5b10"
    }

    fun findFilms(words: String, callback: (FilmSearchResultDTO) -> Unit) {
        val q = URLEncoder.encode(words, "UTF-8")
        val url = "https://api.themoviedb.org/3/search/movie?api_key=${TMDB_KEY3}&query=$q"
        transport.queryText(url) { text ->
            callback(Gson().fromJson(text, FilmSearchResultDTO::class.java))
        }
    }

    fun fetchImage(path: String, callback: (Bitmap) -> Unit) {
        val url = "https://image.tmdb.org/t/p/w300$path"
        transport.queryBytes(url) { bytes ->
            callback(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
        }
    }
}