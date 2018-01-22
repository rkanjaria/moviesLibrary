package com.example.mf.movielibrary.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.activities.movieseriesscreen.MovieSeriesActivity
import files.getYearFromDate
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.PARCELABLE_OBJECT
import files.inflate
import files.loadImage
import files.photoUrl
import kotlinx.android.synthetic.main.movie_recycler_layout.view.*

/**
 * Created by MF on 23-12-2017.
 */
class MovieRecyclerAdapter(val moviesList : List<Movie?>, onLoadMoreListener: OnLoadMoreListener) : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    val MOVIE_VIEW = 1
    val LOADER_VIEW = 2

    private var isMoreDataAvailable = true
    private var isLoading = false

    private val loadMoreListener : OnLoadMoreListener  = onLoadMoreListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder? {

        when (viewType){
            MOVIE_VIEW -> return MovieViewHolder(parent.inflate(R.layout.movie_recycler_layout, false))
            LOADER_VIEW -> return LoaderViewHolder(parent.inflate(R.layout.loader_recycler_layout, false))
            else -> return null
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        if(position >= itemCount -1 && !isLoading && isMoreDataAvailable){
            isLoading = true
            loadMoreListener.loadMore()
        }

        if(getItemViewType(position) == MOVIE_VIEW){
            holder.bindViews(moviesList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {

        if(moviesList.get(position) == null){
            return LOADER_VIEW
        }else{
            return MOVIE_VIEW
        }
    }

    override fun getItemCount(): Int = moviesList.size

    class LoaderViewHolder (loaderView : View) : MovieViewHolder(loaderView)

    open class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private var view : View = itemView

        fun bindViews(movieModel : Movie?){
            view.moviePoster.loadImage(photoUrl + movieModel?.posterPath)
            view.movieName.text = movieModel?.title

            if(movieModel?.releaseDate != null && !movieModel.releaseDate.isBlank()){
                view.movieYear.text = getYearFromDate(movieModel.releaseDate)
            }

            view.setOnClickListener({

                val movieSeriesIntent = Intent(view.context, MovieSeriesActivity ::class.java)
                movieSeriesIntent.putExtra(PARCELABLE_OBJECT, movieModel)
                view.context.startActivity(movieSeriesIntent)
            })
        }
    }

    interface OnLoadMoreListener {
        fun loadMore()
    }

    fun refreshAdapter(lastPosition : Int){
        notifyItemRangeChanged(lastPosition, moviesList.size)
        isLoading = false
    }
}