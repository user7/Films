package com.geekbrains.films

import android.os.Handler
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.geekbrains.films.model.repository.FilmRepository
import com.geekbrains.films.model.repository.transport.rest.FilmItemDTO
import com.geekbrains.films.model.repository.transport.rest.FilmSearchResultDTO
import com.geekbrains.films.viewmodel.FilmsViewModel
import org.junit.Assert.*
import org.junit.Test;
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any


class FilmsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: FilmRepository

    @Mock
    private lateinit var handler: Handler

    private lateinit var filmsViewModel: FilmsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        filmsViewModel = FilmsViewModel(repository, handler)
    }

    @Test
    fun viewModelWorks() {
        val query = "seven samurai"
        var callback: ((FilmSearchResultDTO) -> Unit)? = null
        var realQuery: String? = null
        Mockito.`when`(repository.findFilms(any(), any())).then {
            realQuery = it.getArgument(0)
            callback = it.getArgument(1)
            0
        }
        var accesses = 0
        filmsViewModel.filmList.observeForever { accesses++ }

        filmsViewModel.findFilms(query)
        assertEquals("findFilms changed query", query, realQuery)
        assertNotEquals("findFilms did not pass callback", null, callback)
        assertEquals("films observer has not been once", 2, accesses)
        assertTrue("films isn't empty", filmsViewModel.getData().isEmpty())

        val items = arrayListOf(
            FilmItemDTO(1, "roller blade seven", "artsy", "img://1"),
            FilmItemDTO(2, "six string samurai", "also artsy", "img://2")
        )
        callback!!(FilmSearchResultDTO(1, 1, 2, items))

        assertEquals("films observer has been called 2nd time", 3, accesses)
        assertEquals(
            "title list differs",
            filmsViewModel.getData().map { it.title }.toString(),
            items.map { it.title }.toString()
        )
    }
}