package com.geekbrains.films.model.repository.transport.rest

data class FilmSearchResultDTO(
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: ArrayList<FilmItemDTO>
)