package com.geekbrains.films.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias FilmID = Int
typealias ImageID = String

@Parcelize
data class Film (
    val id: FilmID,
    val title: String,
    val description: String,
    val poster: ImageID,
) : Parcelable

typealias Films = ArrayList<Film>