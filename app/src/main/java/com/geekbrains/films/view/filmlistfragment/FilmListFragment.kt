package com.geekbrains.films.view.filmlistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.films.d
import com.geekbrains.films.databinding.FilmListFragmentBinding
import com.geekbrains.films.services.UrlFetchService
import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmListFragment : Fragment() {
    private val model: FilmsViewModel by viewModel()
    private var _binding: FilmListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FilmListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FilmListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerviewFilms.adapter = FilmListAdapter(model, this@FilmListFragment)
        buttonSearch.setOnClickListener {
            model.findFilms(requireContext(), editSearch.text.toString())
        }
    }

    fun findViewHolder(index: Int): FilmListAdapter.ViewHolder? {
        val v = binding.recyclerviewFilms.findViewHolderForAdapterPosition(index)
        return if (v == null) null else v as FilmListAdapter.ViewHolder
    }
}