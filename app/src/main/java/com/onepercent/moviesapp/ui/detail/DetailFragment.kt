package com.onepercent.moviesapp.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.onepercent.moviesapp.R
import com.onepercent.moviesapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view)

        viewModel.getMovie(args.id)

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            binding.apply {

                Glide
                    .with(this@DetailFragment)
                    .load("https://image.tmdb.org/t/p/original/${movie.posterPath}")
                    .error(R.drawable.poster_placeholder)
                    .listener(
                        object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.isVisible = false
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.isVisible = false

                                textViewMovieTitle.isVisible = true
                                textViewDescription.isVisible = true

                                return false
                            }

                        }
                    )
                    .into(imageView)

                textViewMovieTitle.text = movie.title
                textViewDescription.text = movie.overview

            }
        }

    }
}