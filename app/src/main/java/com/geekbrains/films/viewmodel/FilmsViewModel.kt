package com.geekbrains.films.viewmodel

import androidx.lifecycle.ViewModel
import com.geekbrains.films.model.Film

class FilmsViewModel : ViewModel() {
    private val data: ArrayList<Film> = arrayListOf(
        Film("1", "World War Z", "Zombies running all around."),
        Film("2", "28 Days Later", "Zombies eat British people!")
    )

    fun getData(): List<Film> = data

}