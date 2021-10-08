package com.geekbrains.films.view.filmlistfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.films.R
import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmListFragment : Fragment(R.layout.fragment_film_list) {
    private val model: FilmsViewModel by viewModel()
    private lateinit var adapter: FilmListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FilmListAdapter(model, this)
        view.findViewById<RecyclerView>(R.id.recyclerview_films).adapter = adapter
    }
}