package com.geekbrains.films.model.repository.transport.retrofit

import com.geekbrains.films.model.repository.transport.rest.FilmSearchResultDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbClient {
    @GET("/3/search/movie")
    fun search(
        @Query("api_key") api_key : String,
        @Query("query") query : String
    ) : Call<FilmSearchResultDTO>
}