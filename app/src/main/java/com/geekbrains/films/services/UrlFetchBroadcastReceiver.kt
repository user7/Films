package com.geekbrains.films.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.Toast
import com.geekbrains.films.d
import com.geekbrains.films.model.HttpClient
import com.geekbrains.films.model.rest.FilmSearchResultDTO
import com.geekbrains.films.viewmodel.FilmsViewModel
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

class UrlFetchBroadcastReceiver(val model: FilmsViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        d("event intent=$intent")
        intent?.let {
            val url = intent.getStringExtra(UrlFetchService.KEY_URL)
            val bytes = intent.getByteArrayExtra(UrlFetchService.KEY_BYTES)
            d("event url=$url bytes.len=${bytes?.size}")
            if (bytes != null && url != null) {
                if (url.startsWith("https://image.tmdb.org")) {
                    val image = BitmapFactory.decodeStream(ByteArrayInputStream(bytes))
                    model.handleFetchedImage(url.replace(Regex("^.*/"), ""), image)
                } else {
                    val json = bytes.toString(StandardCharsets.UTF_8)
                    val gson = Gson().fromJson(json, FilmSearchResultDTO::class.java)
                    model.handleSearchResults(gson)
                }
            }
        }
    }
}