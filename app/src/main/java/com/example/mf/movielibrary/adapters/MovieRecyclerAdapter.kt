package com.example.mf.movielibrary.adapters

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.activities.movieseriesscreen.MovieSeriesActivity
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.movie_recycler_layout.view.*
import org.jetbrains.anko.displayMetrics

/**
 * Created by MF on 23-12-2017.
 */
class MovieRecyclerAdapter(val moviesList: List<Movie?>, onMovieSeriesAdapterListener: OnMovieSeriesAdapterListener, val isRecommendation: Boolean = false)
    : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    val MOVIE_VIEW = 1
    val LOADER_VIEW = 2

    private var isMoreDataAvailable = true
    private var isLoading = false

    private val movieSeriesAdapterListener = onMovieSeriesAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder? {

        //val movieLayout = if(isRecommendation) R.layout.similar_movie_recycler_layout else R.layout.movie_recycler_layout

        when (viewType) {
            MOVIE_VIEW -> return MovieViewHolder(parent.inflate(R.layout.movie_recycler_layout, false))
            LOADER_VIEW -> return LoaderViewHolder(parent.inflate(R.layout.loader_recycler_layout, false))
            else -> return null
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        if (position >= itemCount - 1 && !isLoading && isMoreDataAvailable) {
            isLoading = true
            movieSeriesAdapterListener.loadMore()
        }

        if (getItemViewType(position) == MOVIE_VIEW) {
            holder.bindViews(moviesList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {

        if (moviesList.get(position) == null) {
            return LOADER_VIEW
        } else {
            return MOVIE_VIEW
        }
    }

    override fun getItemCount(): Int = moviesList.size

    inner class LoaderViewHolder(loaderView: View) : MovieViewHolder(loaderView)

    inner open class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var view: View = itemView

        fun bindViews(movieModel: Movie?) {

            if(isRecommendation) {
                val widthInDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, view.context.displayMetrics)
                view.layoutParams.width = widthInDp.toInt()
            }

            val placeholder = if(isRecommendation) R.color.darkGrey else R.color.colorPrimary
            view.moviePoster.loadImage(photoUrl + movieModel?.posterPath, placeholder)
            view.movieName.text = movieModel?.title
            view.movieBottomLayout.setBackgroundColor(ContextCompat.getColor(view.context, placeholder))

            if (movieModel?.releaseDate != null && !movieModel.releaseDate.isBlank()) {
                view.movieYear.text = getYearFromDate(movieModel.releaseDate)
            }

            view.setOnClickListener({
                movieSeriesAdapterListener.onMovieOrSeriesClicked(movieModel)
            })
        }
    }

    interface OnMovieSeriesAdapterListener {
        fun loadMore()
        fun onMovieOrSeriesClicked(movieModel: Movie?)
    }

    fun refreshAdapter(lastPosition: Int) {
        notifyItemRangeChanged(lastPosition, moviesList.size)
        isLoading = false
    }
}