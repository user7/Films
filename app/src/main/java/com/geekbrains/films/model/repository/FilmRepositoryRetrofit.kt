package com.geekbrains.films.model.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.geekbrains.films.d
import com.geekbrains.films.model.repository.transport.rest.FilmSearchResultDTO
import com.geekbrains.films.model.repository.transport.retrofit.TmdbClient
import com.geekbrains.films.model.repository.transport.retrofit.TmdbImageClient
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmRepositoryRetrofit : FilmRepository {
    private val findApi: TmdbClient by lazy {
        var httpClient = OkHttpClient.Builder()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        retrofit.create(TmdbClient::class.java)
    }

    private val imageApi: TmdbImageClient by lazy {
        var httpClient = OkHttpClient.Builder()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://image.tmdb.org")
            .client(httpClient.build())
            .build()
        retrofit.create(TmdbImageClient::class.java)
    }

    override fun findFilms(query: String, callback: (FilmSearchResultDTO) -> Unit) {
        findApi.search(FilmRepository.TMDB_KEY3, query)
            .enqueue(object : Callback<FilmSearchResultDTO> {
                override fun onResponse(
                    call: Call<FilmSearchResultDTO>,
                    response: Response<FilmSearchResultDTO>
                ) {
                    if (response.isSuccessful()) {
                        response.body()?.let { callback(it) }
                    } else {
                        d("search bad response ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<FilmSearchResultDTO>, t: Throwable) {
                    d("search error: $t")
                }

            })
    }

    override fun fetchImage(path: String, callback: (Bitmap) -> Unit) {
        imageApi.fetchImage(path).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.bytes()?.let { bytes ->
                        callback(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
                    }
                } else {
                    d("fetchImage bad response ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                d("fetchImage error: $t")
            }
        })
    }
}