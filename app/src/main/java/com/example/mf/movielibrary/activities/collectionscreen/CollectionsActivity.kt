package com.example.mf.movielibrary.activities.collectionscreen

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.collectionmodels.CollectionsResult
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.activity_collections.*

class CollectionsActivity : BaseActivity<CollectionsActivityContract.CollectionsView, CollectionsActivityPresenter>(),
        CollectionsActivityContract.CollectionsView, MovieRecyclerAdapter.OnMovieSeriesAdapterListener {

    override var mPresenter = CollectionsActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)

        initToolbar(toolbar as Toolbar, true, intent.getStringExtra(NAME))
        collectionPosterImage.loadDrawableImage(intent.getIntExtra(BACKDROP_PATH, 0), R.color.darkGrey)
        mPresenter.callGetListApi(intent.getIntExtra(INT_ID, 0))
    }

    override fun setCollectionsRecyclerview(collectionsResult: CollectionsResult) {

        if (collectionsResult.moviesList != null && collectionsResult.moviesList.isNotEmpty()) {
            collectionsRecylerview.layoutManager = GridLayoutManager(this, 3)
            collectionsRecylerview.adapter = MovieRecyclerAdapter(collectionsResult.moviesList, this)
        }
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        mPresenter.launchMoviesOrSeriesActivity(movieModel)
    }

}
