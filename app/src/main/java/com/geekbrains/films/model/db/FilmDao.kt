package com.geekbrains.films.model.db

import androidx.room.*
import com.geekbrains.films.model.FilmID

@Dao
interface FilmDao {
    @Query("SELECT * FROM FilmEntity WHERE id = :id")
    fun queryFilm(id: FilmID): List<FilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertFilm(filmEntity: FilmEntity)

    @Delete
    fun deleteFilm(filmEntity: FilmEntity)
}