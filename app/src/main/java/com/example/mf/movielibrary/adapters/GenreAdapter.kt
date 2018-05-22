package com.example.mf.movielibrary.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.genremodel.Genre
import files.inflate

class GenreAdapter(val genreList: List<Genre>) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(parent.inflate(R.layout.genre_recyler_layout, false))
    }

    override fun getItemCount() = genreList.size

    override fun onBindViewHolder(holder: GenreViewHolder?, position: Int) {
        holder?.bindViews(genreList[position])
    }

    inner class GenreViewHolder(genreView : View) : RecyclerView.ViewHolder(genreView){
        val view = genreView

        fun bindViews(genre : Genre){

        }
    }
}
