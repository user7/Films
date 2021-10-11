package com.geekbrains.films.view.filmlistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.films.R
import com.geekbrains.films.d
import com.geekbrains.films.view.filmdetailsfragment.FilmDetailsFragment
import com.geekbrains.films.viewmodel.FilmsViewModel

class FilmListAdapter(val model: FilmsViewModel, val filmListFragment: FilmListFragment) :
    RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.film_list_item_title)

        init {
            view.setOnClickListener {
                val film = model.getData()[adapterPosition]
                val manager = filmListFragment.activity?.supportFragmentManager
                manager?.let {
                    val bundle = Bundle()
                    bundle.putParcelable(FilmDetailsFragment.BUNDLE_ID, film)
                    val fragment = FilmDetailsFragment.newInstance(bundle)
                    it.beginTransaction()
                        .add(R.id.film_list_fragment_container, fragment)
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.film_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = model.getData()[position].title
    }

    override fun getItemCount(): Int = model.getData().size
}