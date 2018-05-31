package com.example.mf.movielibrary.activities.homescreen

import android.app.AlertDialog
import android.app.FragmentTransaction
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.fragments.HomeFragment
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.activity_home.*


/**
 * Created by RK on 28-11-2017.
 */
class HomeActivity : BaseActivity<HomeActivityContract.HomeView, HomeActivityPresenter>(),
        HomeActivityContract.HomeView, HomeFragment.HomeFragmentListener {

    private val homeFragment = HomeFragment()

    override fun callGetMoviesApi(movieOrSeries: String, type: String, page: Int) {
        mPresenter.callGetMoviesApi(movieOrSeries, type, page)
    }

    override var mPresenter = HomeActivityPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, homeFragment).commit()

        supportActionBar?.title = "Movies"
        navigationView.setCheckedItem(R.id.action_movies)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_movies -> {
                    item.setChecked(true)
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Movies"
                    homeFragment.callMoviesOrSeriesApi(MOVIE)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_tv_shows -> {
                    item.setChecked(true)
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Tv Shows"
                    homeFragment.callMoviesOrSeriesApi(TV_SHOWS)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_search -> {
                    drawerLayout.closeDrawers()
                    homeFragment.onClickSearch()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_favourites -> {
                    drawerLayout.closeDrawers()
                    mPresenter.requestFavouritesActivity()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_collections -> {
                    drawerLayout.closeDrawers()
                    mPresenter.requestCollectionsActivity()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_people -> {
                    drawerLayout.closeDrawers()
                    mPresenter.requestActorsActivity()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    override fun showMovieOrSeriesTypeDialog() {
        homeFragment.showMovieOrSeriesTypeDialog()
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?, movieOrSeries: String) {
        mPresenter.launchMovieSeriesActivity(movieModel, movieOrSeries)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
            R.id.action_sort -> mPresenter.requestMovieOrSeriesTypeDialog()
            R.id.action_search -> homeFragment.onClickSearch()
        }
        return true
    }

    override fun setMovieRecyclerView(moviesList: List<Movie?>?, totalResults: Int) {
        homeFragment.setRecyclerView(moviesList, totalResults)
    }

    override fun onClickSearchActivity(movieOrSeries: String) {
        mPresenter.requestSearchActivity(movieOrSeries)
    }

    override fun showProgressBar() {
        homeFragment.showProgressBar()
    }

    override fun hideProgressBar() {
        homeFragment.hideProgressBar()
    }
}
