package com.example.mf.movielibrary.activities.collectionscreen

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.collectionmodels.CollectionsResult
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.BACKDROP_PATH
import files.INT_ID
import files.NAME
import files.runLayoutAnimation
import kotlinx.android.synthetic.main.activity_collections.*

class CollectionsActivity : BaseActivity<CollectionsActivityContract.CollectionsView, CollectionsActivityPresenter>(),
        CollectionsActivityContract.CollectionsView, MovieRecyclerAdapter.OnMovieSeriesAdapterListener {

    override var mPresenter = CollectionsActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)
        supportPostponeEnterTransition()

        initToolbar(toolbar as Toolbar, true, intent.getStringExtra(NAME))
        //collectionPosterImage.loadDrawableImage(intent.getIntExtra(BACKDROP_PATH, 0), R.color.darkGrey)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) postponeEnterTransition()
        Glide.with(this)
                .load(intent.getIntExtra(BACKDROP_PATH, 0))
                .apply(RequestOptions.placeholderOf(R.color.darkGrey))
                .apply(RequestOptions().dontTransform())
                .apply(RequestOptions.errorOf(R.color.darkGrey))
                .apply(RequestOptions().dontAnimate())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                }).into(collectionPosterImage)
        callGetCollectionListApi()
    }

    private fun callGetCollectionListApi() {
        mPresenter.callGetListApi(intent.getIntExtra(INT_ID, 0))
    }

    override fun setCollectionsRecyclerview(collectionsResult: CollectionsResult) {
        if (collectionsResult.moviesList != null && collectionsResult.moviesList.isNotEmpty()) {
            collectionsRecylerview.layoutManager = GridLayoutManager(this, 3)
            collectionsRecylerview.adapter = MovieRecyclerAdapter(collectionsResult.moviesList, this)
            runLayoutAnimation(collectionsRecylerview, R.anim.grid_layout_animation_fall_down)
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

    override fun onSnackBarButtonClicked() {
        callGetCollectionListApi()
    }
}
