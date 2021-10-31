package com.geekbrains.films.model.repository.threaded

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.geekbrains.films.d
import com.geekbrains.films.model.repository.FilmRepository
import com.geekbrains.films.model.rest.FilmSearchResultDTO
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.net.URLEncoder

class ThreadedFilmRepository : FilmRepository {

    override fun findFilms(words: String, callback: (FilmSearchResultDTO) -> Unit) {
        val q = URLEncoder.encode(words, "UTF-8")
        val url =
            "https://api.themoviedb.org/3/search/movie?api_key=${FilmRepository.KEY3}&query=$q"
        Thread {
            try {
                val json = BlockingHttpClient.queryText(url)
                callback(Gson().fromJson(json, FilmSearchResultDTO::class.java))
            } catch (e : Exception) {
                d("search query error: $e")
            }
        }.start()
    }

    override fun fetchImage(path: String, callback: (Bitmap) -> Unit) {
        val url = "https://image.tmdb.org/t/p/w300$path"
        Thread {
            try {
                val bytes = BlockingHttpClient.queryBytes(url)
                callback(BitmapFactory.decodeStream(ByteArrayInputStream(bytes)))
            } catch (e : Exception) {
                d("image query error: $e")
            }
        }.start()
    }
}