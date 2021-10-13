package com.geekbrains.films.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film (
    val id: String,
    val title: String,
    val description: String
) : Parcelable