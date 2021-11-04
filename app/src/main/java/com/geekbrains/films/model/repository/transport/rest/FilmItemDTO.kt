package com.geekbrains.films.model.repository.transport.rest

data class FilmItemDTO(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val poster_path: String?
)
