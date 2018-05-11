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
import files.INT_ID
import files.NAME
import files.calculateNoOfColumns
import files.loadImage
import kotlinx.android.synthetic.main.activity_collections.*

class CollectionsActivity : BaseActivity<CollectionsActivityContract.CollectionsView, CollectionsActivityPresenter>(),
        CollectionsActivityContract.CollectionsView, MovieRecyclerAdapter.OnMovieSeriesAdapterListener {

    override var mPresenter = CollectionsActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)

        initToolbar(toolbar as Toolbar, true, intent.getStringExtra(NAME))
        mPresenter.callGetListApi(intent.getIntExtra(INT_ID, 0))
    }

    override fun setCollectionsRecyclerview(collectionsResult: CollectionsResult) {

        val imagePath = if (collectionsResult.posterPath != null) collectionsResult.posterPath else ""
        collectionPosterImage.loadImage(imagePath, R.color.darkGrey)

        if (collectionsResult.moviesList != null && collectionsResult.moviesList.isNotEmpty()) {
            collectionsRecylerview.layoutManager = GridLayoutManager(this, calculateNoOfColumns(this, 110))
            collectionsRecylerview.adapter = MovieRecyclerAdapter(collectionsResult.moviesList, this)
        }
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

}
