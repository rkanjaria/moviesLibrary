package com.example.mf.movielibrary.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import files.MOVIE_OR_SERIES
import files.database
import kotlinx.android.synthetic.main.fragment_favourites.*


/**
 * A simple [Fragment] subclass.
 */
class FavouritesFragment : Fragment(), MovieRecyclerAdapter.OnMovieSeriesAdapterListener {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater!!.inflate(R.layout.fragment_favourites, container, false)
        initFragment()
        return rootView
    }

    fun initFragment(){
        val moviesList = context.database.getAllMoviesOrTvShows(arguments.getString(MOVIE_OR_SERIES))
        favouritesRecyclerview.setHasFixedSize(true)
        favouritesRecyclerview.layoutManager = GridLayoutManager(context, 3)
        favouritesRecyclerview.adapter = MovieRecyclerAdapter(moviesList, this)
    }
}
