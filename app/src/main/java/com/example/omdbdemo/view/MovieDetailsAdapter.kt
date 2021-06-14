package com.example.omdbdemo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbdemo.R
import com.example.omdbdemo.data.MovieCategories
import com.example.omdbdemo.data.MovieGenericItem
import com.example.omdbdemo.data.MoviePosterItem
import com.example.omdbdemo.data.MovieTitleItem
import com.example.omdbdemo.databinding.RowMovieCategoryBinding
import com.example.omdbdemo.databinding.RowMoviePlotBinding
import com.example.omdbdemo.databinding.RowMoviePosterBinding
import com.example.omdbdemo.databinding.RowMovieTitleBinding
import com.google.android.material.chip.Chip

const val VIEW_TYPE_TITLE = 0;
const val VIEW_TYPE_POSTER = 1;
const val VIEW_TYPE_GENERIC = 2;
const val VIEW_TYPE_CATEGORY = 3;

class MovieDetailsAdapter(private val items: List<Any?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_TYPE_TITLE -> {
                return MovieTitleViewHolder(
                    RowMovieTitleBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_POSTER -> {
                return MoviePosterViewHolder(
                    RowMoviePosterBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_GENERIC -> {
                return MovieGenericViewHolder(
                    RowMoviePlotBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_CATEGORY -> {
                return MovieCategoryViewHolder(
                    RowMovieCategoryBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return MovieGenericViewHolder(
                    RowMoviePlotBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_TITLE -> (holder as? MovieTitleViewHolder)?.bind(
                items?.get(
                    position
                ) as? MovieTitleItem
            )
            VIEW_TYPE_POSTER -> (holder as? MoviePosterViewHolder)?.bind(
                items?.get(
                    position
                ) as? MoviePosterItem
            )
            VIEW_TYPE_GENERIC -> (holder as? MovieGenericViewHolder)?.bind(
                items?.get(
                    position
                ) as? MovieGenericItem
            )
            VIEW_TYPE_CATEGORY -> (holder as? MovieCategoryViewHolder)?.bind(
                items?.get(
                    position
                ) as? MovieCategories

            )
        }
    }

    override fun getItemCount(): Int {
        return items?.count() ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        when (items?.get(position)) {
            is MovieTitleItem -> return VIEW_TYPE_TITLE
            is MoviePosterItem -> return VIEW_TYPE_POSTER
            is MovieGenericItem -> return VIEW_TYPE_GENERIC
            is MovieCategories -> return VIEW_TYPE_CATEGORY
            else -> return VIEW_TYPE_TITLE
        }
    }


    inner class MovieTitleViewHolder(val binding: RowMovieTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieTitleItem?) {
            binding?.model = model
            binding?.executePendingBindings()
        }
    }


    inner class MoviePosterViewHolder(val binding: RowMoviePosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MoviePosterItem?) {
            binding?.model = model
            binding?.executePendingBindings()
        }
    }


    inner class MovieGenericViewHolder(val binding: RowMoviePlotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieGenericItem?) {
            binding?.model = model
            binding?.executePendingBindings()
        }
    }


    inner class MovieCategoryViewHolder(val binding: RowMovieCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieCategories?) {
            binding?.model = model
            binding?.model?.categories?.forEach {
                val chip =
                    LayoutInflater.from(binding.root.context)
                        .inflate(R.layout.row_chip_category, null) as Chip
                chip.setText(it)
                binding?.cgCategory.addView(chip)
            }


            binding?.executePendingBindings()
        }
    }
}
