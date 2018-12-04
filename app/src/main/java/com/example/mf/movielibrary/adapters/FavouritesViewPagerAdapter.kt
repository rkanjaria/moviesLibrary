package com.example.mf.movielibrary.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.mf.movielibrary.fragments.FavouritesFragment
import com.example.mf.movielibrary.files.MOVIE
import com.example.mf.movielibrary.files.MOVIE_OR_SERIES
import com.example.mf.movielibrary.files.TV_SHOWS

/**
 * Created by RK on 05-04-2018.
 */
class FavouritesViewPagerAdapter(fragmentManager: FragmentManager, val fragmentTitleList: Array<String>) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {

        val bundle = Bundle()

        when (position) {
            0 -> bundle.putString(MOVIE_OR_SERIES, MOVIE)
            1 -> bundle.putString(MOVIE_OR_SERIES, TV_SHOWS)
        }

        val favouritesFragment = FavouritesFragment()
        favouritesFragment.arguments = bundle
        return favouritesFragment
    }

    override fun getCount() = fragmentTitleList.size

    override fun getPageTitle(position: Int) = fragmentTitleList[position]
}