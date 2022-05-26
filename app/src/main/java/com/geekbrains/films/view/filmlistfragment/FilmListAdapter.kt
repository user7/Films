package com.geekbrains.films.view.filmlistfragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.films.R
import com.geekbrains.films.d
import com.geekbrains.films.view.filmdetailsfragment.FilmDetailsFragment
import com.geekbrains.films.viewmodel.FilmsViewModel

class FilmListAdapter(val model: FilmsViewModel, val filmListFragment: FilmListFragment) :
    RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {

    init {
        model.filmList.observe(filmListFragment.viewLifecycleOwner) {
            d("dataset changed $it")
            notifyDataSetChanged()
        }

        model.imageAvailable.observe(filmListFragment.viewLifecycleOwner) { imageID ->
            d("image updated $imageID")
            model.getData().forEachIndexed { index, element ->
                model.needsShowing(element.poster)?.let { bitmap ->
                    filmListFragment.findViewHolder(index)?.let { viewHolder ->
                        viewHolder.setImageBitmap(bitmap)
                        notifyItemChanged(index)
                    }
                }
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.film_list_item_title)
        val image: ImageView = view.findViewById(R.id.film_list_item_image)

        init {
            view.setOnClickListener {
                val film = model.getData()[adapterPosition]
                filmListFragment.activity?.supportFragmentManager?.let { manager ->
                    val bundle = Bundle()
                    bundle.putParcelable(FilmDetailsFragment.BUNDLE_ID, film)
                    val detailsFragment = FilmDetailsFragment.newInstance(bundle)
                    val listFragment = manager.findFragmentByTag("film_list_fragment_tag")!!
                    manager.beginTransaction()
                        .add(R.id.film_list_fragment_container, detailsFragment)
                        .hide(listFragment)
                        .addToBackStack("")
                        .commit()
                }
            }
        }

        fun setImageBitmap(bitmap: Bitmap?) {
            if (bitmap == null) {
                image.setImageResource(R.drawable.ic_no_image_available)
            } else {
                image.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.film_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = model.getData()[position]
        holder.title.text = film.title
        holder.setImageBitmap(model.getImage(film.poster))
    }

    override fun getItemCount(): Int = model.getData().size
}