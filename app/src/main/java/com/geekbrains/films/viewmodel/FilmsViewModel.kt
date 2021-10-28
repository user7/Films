package com.geekbrains.films.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.films.d
import com.geekbrains.films.model.*
import com.geekbrains.films.model.rest.FilmSearchResultDTO
import com.geekbrains.films.services.UrlFetchService

class FilmsViewModel : ViewModel() {
    private val handler = Handler(Looper.getMainLooper())
    private val images = HashMap<ImageID, Bitmap>()
    private val mFilmList = MutableLiveData(Films())
    var filmList: LiveData<Films> = mFilmList
    fun getData() = filmList.value!!

    private val mImageAvailable = MutableLiveData<ImageID>()
    var imageAvailable: LiveData<ImageID> = mImageAvailable

    fun getImage(id: ImageID): Bitmap? {
        val bitmap = images.getOrDefault(id, null)
        if (bitmap == null) {
            FilmRepository.fetchImage(id)
        }
        return bitmap
    }

    fun handleFetchedImage(id: ImageID, bitmap: Bitmap) {
        images.set(id, bitmap)
        mImageAvailable.postValue(id)
    }

    fun findFilms(context: Context, words: String) {
        if (words.isEmpty())
            return

        mFilmList.postValue(Films()) // clear current films list
        FilmRepository.findFilms(words)
    }

    fun handleSearchResults(result: FilmSearchResultDTO) {
        val oldData = getData()
        val newData = oldData.clone() as Films
        for (f in result.results) {
            if (f.id == null || f.title == null) {
                d("invalid entry in search results: $f")
                continue
            }
            val film = Film(
                f.id,
                f.title,
                f.overview ?: "",
                f.poster_path ?: ""
            )
            newData.add(film)
        }
        mFilmList.postValue(newData)
    }
}