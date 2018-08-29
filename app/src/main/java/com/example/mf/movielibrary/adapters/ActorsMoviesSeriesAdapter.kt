package com.example.mf.movielibrary.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.mf.movielibrary.fragments.ActorsMovieTvShowsFragment
import files.POSITION

class ActorsMoviesSeriesAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val titles = mutableListOf<String>()

    override fun getItem(position: Int): Fragment {
        val fragment = ActorsMovieTvShowsFragment()
        val bundle = Bundle()
        bundle.putInt(POSITION, position)
        fragment.arguments = bundle
        return fragment
    }

    fun addFragment(title: String) {
        titles.add(title)
    }

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) = titles[position]
}