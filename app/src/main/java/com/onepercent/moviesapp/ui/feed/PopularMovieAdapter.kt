package com.onepercent.moviesapp.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import com.onepercent.moviesapp.R
import com.onepercent.moviesapp.databinding.ItemMovieCardBinding
import com.onepercent.moviesapp.model.MovieEntity

class PopularMovieAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<MovieEntity, PopularMovieAdapter.MovieViewHolder>(
    MOVIE_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val binding = ItemMovieCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class MovieViewHolder(private val binding: ItemMovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.imageView.outlineProvider = ViewOutlineProvider.BACKGROUND
            binding.imageView.clipToOutline = true

            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item.id)
                    }
                }

            }
        }

        fun bind(movie: MovieEntity) {
            binding.apply {
                Glide
                    .with(itemView)
                    .load("https://image.tmdb.org/t/p/original/${movie.posterPath}")
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.poster_placeholder)
                    .into(imageView)

                textViewMovieTitle.text = movie.title
                textViewMovieDate.text = movie.releaseDate

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieEntity>() {

            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
                oldItem == newItem
        }
    }
}