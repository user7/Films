package com.geekbrains.films.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.films.R
import com.geekbrains.films.viewmodel.FilmsViewModel

class FilmListFragment : Fragment(R.layout.fragment_film_list) {
    private val model: FilmsViewModel by activityViewModels()
    private lateinit var adapter: FilmListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FilmListAdapter(model, this)
        view.findViewById<RecyclerView>(R.id.recyclerview_films).adapter = adapter
    }
}