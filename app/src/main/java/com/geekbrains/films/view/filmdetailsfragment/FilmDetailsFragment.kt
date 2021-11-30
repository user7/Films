package com.geekbrains.films.view.filmdetailsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.geekbrains.films.databinding.FilmDetailsFragmentBinding
import com.geekbrains.films.model.Film
import com.geekbrains.films.model.ImageID
import com.geekbrains.films.model.db.FilmDatabaseHolder
import com.geekbrains.films.model.db.FilmEntity
import com.geekbrains.films.viewmodel.FilmsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FilmDetailsFragment : Fragment() {
    private var _binding: FilmDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val model by sharedViewModel<FilmsViewModel>()
    private var imageID = ImageID()
    private var lastComment = ""
    private val dbh: FilmDatabaseHolder by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilmDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Film>(BUNDLE_ID)?.let { film ->
            binding.filmDetailsDescription.text = film.description
            binding.filmDetailsTitle.text = film.title

            // заметка к фильму
            val comment = dbh.dao().queryFilm(film.id).getOrNull(0)?.comment ?: ""
            binding.filmDetailsComment.setText(comment)
            binding.filmDetailsCommentSave.isEnabled = false
            lastComment = comment
            // кнопка save включена, когда текст отличается от lastComment
            binding.filmDetailsComment.doOnTextChanged { text, start, before, count ->
                binding.filmDetailsCommentSave.isEnabled =
                    binding.filmDetailsComment.text.toString() != lastComment
            }
            binding.filmDetailsCommentSave.setOnClickListener {
                lastComment = binding.filmDetailsComment.text.toString()
                dbh.dao().upsertFilm(FilmEntity(film.id, lastComment))
            }
            binding.filmDetailsCommentDiscard.setOnClickListener {
                binding.filmDetailsComment.setText(lastComment)
                binding.filmDetailsCommentSave.isEnabled = false
            }

            imageID = film.poster
            model.getImage(imageID)?.let { binding.filmDetailsPoster.setImageBitmap(it) }
            model.imageAvailable.observe(viewLifecycleOwner) {
                if (it == imageID)
                    binding.filmDetailsPoster.setImageBitmap(model.getImage(imageID))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BUNDLE_ID = "Film"

        fun newInstance(bundle: Bundle): FilmDetailsFragment {
            val fragment = FilmDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}