package com.example.mf.movielibrary.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.mf.movielibrary.fragments.ActorsMovieSeriesFragment
import com.example.mf.movielibrary.fragments.FavouritesFragment
import files.MOVIE
import files.MOVIE_OR_SERIES
import files.TV_SHOWS

class ActorsMoviesSeriesAdapter(fragmentManager: FragmentManager, val fragmentTitleList: Array<String>) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {

        val bundle = Bundle()

        when (position) {
            0 -> bundle.putString(MOVIE_OR_SERIES, MOVIE)
            1 -> bundle.putString(MOVIE_OR_SERIES, TV_SHOWS)
        }

        val actorsMoviesSeriesFragment = ActorsMovieSeriesFragment()
        actorsMoviesSeriesFragment.arguments = bundle
        return actorsMoviesSeriesFragment
    }

    override fun getCount() = fragmentTitleList.size

    override fun getPageTitle(position: Int) = fragmentTitleList[position]
}