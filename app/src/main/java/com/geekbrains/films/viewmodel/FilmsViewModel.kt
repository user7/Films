package com.geekbrains.films.viewmodel

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.films.d
import com.geekbrains.films.model.*
import com.geekbrains.films.model.repository.FilmRepository
import com.geekbrains.films.model.repository.transport.rest.FilmSearchResultDTO

class FilmsViewModel(
    private val filmRepository: FilmRepository,
    private val handler: Handler = Handler(Looper.getMainLooper()),
) : ViewModel() {

    private class BitmapHolder(var bitmap: Bitmap? = null, var shown: Boolean = false);

    private val images = HashMap<ImageID, BitmapHolder>()
    private val mFilmList = MutableLiveData(Films())
    var filmList: LiveData<Films> = mFilmList
    fun getData() = filmList.value!!

    private val mImageAvailable = MutableLiveData<ImageID>()
    var imageAvailable: LiveData<ImageID> = mImageAvailable

    private val mAppSettings = MutableLiveData(AppSettings())
    var appSettings: LiveData<AppSettings> = mAppSettings

    fun getImage(id: ImageID): Bitmap? {
        val bitmapHolder = images.getOrDefault(id, null)
        if (bitmapHolder == null) {
            images[id] = BitmapHolder()
            filmRepository.fetchImage(id) { bitmap ->
                handler.post {
                    images.getOrDefault(id, null)?.bitmap = bitmap
                    mImageAvailable.postValue(id)
                }
            }
        }
        return bitmapHolder?.bitmap
    }

    fun needsShowing(id: ImageID) : Bitmap? {
        val holder = images.getOrDefault(id, null)
        if (holder?.bitmap == null || holder.shown)
            return null
        holder.shown = true
        return holder.bitmap
    }

    fun findFilms(words: String) {
        if (words.isEmpty()) {
            return
        }

        mFilmList.postValue(Films()) // clear current films list
        filmRepository.findFilms(words) { handleSearchResults(it) }
    }

    private fun handleSearchResults(result: FilmSearchResultDTO) {
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

    fun setAdultContent(enabled: Boolean) {
        mAppSettings.postValue(mAppSettings.value?.copy(adultContent = enabled))
    }
}