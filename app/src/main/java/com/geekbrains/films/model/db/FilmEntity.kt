package com.geekbrains.films.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geekbrains.films.model.FilmID

@Entity
data class FilmEntity(
    @PrimaryKey(autoGenerate = true)
    var id: FilmID = 0,
    var comment: String = ""
)