package com.example.mf.movielibrary.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.Movie
import kotlinx.android.synthetic.main.movie_recycler_layout.view.*
import files.inflate
import files.loadImage

/**
 * Created by MF on 23-12-2017.
 */
class MovieRecyclerAdapter(val moviesList : List<Movie>) : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflatedView = parent.inflate(R.layout.movie_recycler_layout, false)
        return MovieViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieModel = moviesList[position]
        holder.bindViews(movieModel)
    }

    override fun getItemCount(): Int = moviesList.size

    class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private var view : View = itemView
        private var movieModel : Movie? = null

        fun bindViews(movieModel : Movie){
            view.movie_poster.loadImage(movieModel.posterPath)
        }
    }

}