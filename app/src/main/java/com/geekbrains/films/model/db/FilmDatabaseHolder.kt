package com.geekbrains.films.model.db

import android.content.Context
import androidx.room.Room
import com.geekbrains.films.d

class FilmDatabaseHolder(context: Context) {
    private lateinit var filmDatabase: FilmDatabase

    init {
        filmDatabase = Room.databaseBuilder(
            context,
            FilmDatabase::class.java,
            "film.db"
        )
            .allowMainThreadQueries()
            .build()
        d("created db")
    }

    fun dao() = filmDatabase.filmDao()
}