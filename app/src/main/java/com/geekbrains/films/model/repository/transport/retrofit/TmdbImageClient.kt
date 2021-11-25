package com.geekbrains.films.model.repository.transport.retrofit

import com.geekbrains.films.model.repository.transport.rest.FilmSearchResultDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TmdbImageClient {
    @GET("/t/p/w300/{path}")
    fun fetchImage(
        @Path("path") path : String
    ) : Call<ResponseBody>
}