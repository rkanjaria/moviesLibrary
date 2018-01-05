package com.example.mf.movielibrary.activities.homescreen

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.Movie
import kotlinx.android.synthetic.main.activity_home.*


/**
 * Created by MF on 28-11-2017.
 */
class HomeActivity : BaseActivity<HomeActivityContract.HomeView, HomeActivityPresenter>(),
        HomeActivityContract.HomeView, MovieRecyclerAdapter.OnLoadMoreListener{

    private lateinit var mMoviesList : MutableList<Movie?>
    private lateinit var movieAdapter : MovieRecyclerAdapter
    private var page = 1

    private var movieOrSeries = "movie"
    private var type = "popular"
    override var mPresenter: HomeActivityPresenter = HomeActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initToolbar(toolbar as Toolbar, true, "Movies")
        mPresenter.callGetMoviesApi(movieOrSeries, type, page)

    }

    override fun setMovieRecyclerView(moviesList: List<Movie?> ?) {

        if(moviesList != null){

            mMoviesList = moviesList.toMutableList()

            movieRecyclerView.layoutManager = GridLayoutManager(this, 3)
            movieAdapter = MovieRecyclerAdapter(mMoviesList, this)
            movieRecyclerView.adapter = movieAdapter
        }



        /*if(moviesList != null) {

            if(mMoviesList.isEmpty()){

                //when data loads for first time set recyclerview
                mMoviesList.addAll(moviesList)
                movieAdapter.notifyDataSetChanged()



            }else {
                // after data is loaded just add new list to list and refresh tha data
                mMoviesList.removeAt(mMoviesList.size - 1)

                if(moviesList.isNotEmpty()){
                    mMoviesList.addAll(moviesList)
                }else{
                    movieAdapter.setIsMoreDataAvailable(false)
                }
                movieAdapter.notifyDataSetChanged()
            }
        }*/
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun loadMore() {
        movieRecyclerView.post({loadMoreData()})
    }

    private fun loadMoreData(){

        page ++
        mMoviesList.add(null)
        movieAdapter.notifyItemInserted(mMoviesList.size - 1)
        mPresenter.callGetMoviesApi(movieOrSeries, type, page)
    }
}
