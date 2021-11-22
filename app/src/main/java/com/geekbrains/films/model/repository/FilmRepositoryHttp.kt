package com.geekbrains.films.model.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.geekbrains.films.model.repository.transport.HttpTransport
import com.geekbrains.films.model.repository.transport.rest.FilmSearchResultDTO
import com.google.gson.Gson
import java.net.URLEncoder

class FilmRepositoryHttp(val transport: HttpTransport) : FilmRepository {
    override fun findFilms(words: String, callback: (FilmSearchResultDTO) -> Unit) {
        val q = URLEncoder.encode(words, "UTF-8")
        val url = "https://api.themoviedb.org/3/search/movie?api_key=${FilmRepository.TMDB_KEY3}&query=$q"
        transport.queryText(url) { text ->
            callback(Gson().fromJson(text, FilmSearchResultDTO::class.java))
        }
    }

    override fun fetchImage(path: String, callback: (Bitmap) -> Unit) {
        val url = "https://image.tmdb.org/t/p/w300$path"
        transport.queryBytes(url) { bytes ->
            callback(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
        }
    }
}