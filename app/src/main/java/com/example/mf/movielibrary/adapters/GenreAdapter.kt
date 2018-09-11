package com.example.mf.movielibrary.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.database.entities.Genre
import files.inflate
import kotlinx.android.synthetic.main.genre_recyler_layout.view.*

class GenreAdapter(val genreList: List<Genre>, val genreListener: GenreAdapterListener?) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(parent.inflate(R.layout.genre_recyler_layout, false))
    }

    override fun getItemCount() = genreList.size

    override fun onBindViewHolder(holder: GenreViewHolder?, position: Int) {
        holder?.bindViews(genreList[position], position)
    }

    fun removeSeletedGenre() {
        selectedPosition = -1
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(genreView: View) : RecyclerView.ViewHolder(genreView) {
        val view = genreView
        fun bindViews(genre: Genre, position: Int) {
            view.genreName.text = genre.genreName

            if (position == selectedPosition) {
                view.genreName.background = ContextCompat.getDrawable(view.context, R.drawable.tag_background_colored_drawable)
                view.genreName.setTextColor(ContextCompat.getColor(view.context, R.color.colorPrimary))
            } else {
                view.genreName.background = ContextCompat.getDrawable(view.context, R.drawable.tag_background_drawable)
                view.genreName.setTextColor(ContextCompat.getColor(view.context, android.R.color.secondary_text_dark))
            }

            view.setOnClickListener {
                genreListener?.onGenreSelected(genre.genreId)
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    interface GenreAdapterListener {
        fun onGenreSelected(genreId: Int) {}
    }
}
