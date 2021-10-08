package com.geekbrains.films.view.filmlistfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.films.R
import com.geekbrains.films.viewmodel.FilmsViewModel

class FilmListAdapter(val model: FilmsViewModel, val filmListFragment: FilmListFragment) :
    RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.film_list_item_title)
        val desc: TextView = view.findViewById(R.id.film_list_item_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = model.getData()[position].title
        holder.desc.text = model.getData()[position].description
    }

    override fun getItemCount(): Int = model.getData().size
}