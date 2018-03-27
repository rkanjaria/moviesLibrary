package com.example.mf.movielibrary.activities.searchscreen

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.activities.movieseriesscreen.MovieSeriesActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.genremodel.Genre
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.MOVIE_OR_SERIES
import files.PARCELABLE_OBJECT
import files.database
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.displayMetrics

/**
 * Created by MF on 08-02-2018.
 */
class SearchActivityPresenter : BasePresenterImpl<SearchActivityContract.SearchBaseView>(),
        SearchActivityContract.SearchPresenter {

    val context = mView?.getContext()

    override fun getGenreFromDbAndCreateBottomSheet(movieOrSeries: String, genreBottomSheet: LinearLayout) {
        val genres = context?.database?.getAllGenres(movieOrSeries)
        if (genres != null) {
            createTagsLayout(genres, genreBottomSheet)
        }
    }


    fun createTagsLayout(tagsList: List<Genre>, genreBottomSheet: LinearLayout) {

        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, context?.displayMetrics).toInt()
        var previousTagWidth = 0
        var previousTagHeight = 0
        var left = 0
        var top = 0

        val displayMetrices = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrices)
        val screenWidth = displayMetrices.widthPixels

        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val tagLayout = RelativeLayout(context)
        tagLayout.setPadding(px, px * 2, px, px * 2)
        tagLayout.layoutParams = params

        tagsList.forEach {
            val tagView = TextView(context)
            tagView.setPadding(px * 2, px, px * 2, px)
            /*tagView.text = tagsList.get(tagsList.indexOf(it))
            tagView.tag = tagsList.get(tagsList.indexOf(it))*/
            tagView.text = tagsList.get(tagsList.indexOf(it)).genreName
            tagView.tag = tagsList.get(tagsList.indexOf(it)).genreId
            tagView.background = ContextCompat.getDrawable(context, R.drawable.tag_background_drawable)
            tagView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            tagView.typeface = ResourcesCompat.getFont(context, R.font.noto_sans_regular)
            tagView.textSize = 12f
            tagView.maxLines = 1
            tagView.gravity = Gravity.CENTER
            tagView.ellipsize = TextUtils.TruncateAt.END

            val tagViewParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            left = left + previousTagWidth

            if (((screenWidth - px * 2) - left) < tagView.measureTextViewWidth()) {
                top = top + previousTagHeight
                left = 0
            }

            tagViewParams.setMargins(left, top, 0, 0)
            tagView.layoutParams = tagViewParams
            tagLayout.addView(tagView)

            previousTagWidth = tagView.measureTextViewWidth() + px
            previousTagHeight = tagView.measureTextViewHeight() + px
        }

        genreBottomSheet.addView(tagLayout)
    }

    fun TextView.measureTextViewWidth(): Int {
        this.measure(0, 0)
        return this.measuredWidth
    }

    fun TextView.measureTextViewHeight(): Int {
        this.measure(0, 0)
        return this.measuredHeight
    }


    override fun launchMovieSeriesActivity(movieModel: Movie?, movieOrSeries: String) {
        val movieSeriesIntent = Intent(mView?.getContext(), MovieSeriesActivity::class.java)
        movieSeriesIntent.putExtra(PARCELABLE_OBJECT, movieModel)
        movieSeriesIntent.putExtra(MOVIE_OR_SERIES, movieOrSeries)
        mView?.getContext()?.startActivity(movieSeriesIntent)
    }

    override fun callSearchMovieOrSeriesByName(movieOrSeries: String, query: String?, queryPage: Int) {
        if (queryPage == 1) {
            mView?.showProgressBar()
        }

        if (query != null && query.isNotEmpty()) {

            RetrofitHelper.create().doSearchMovieOrSeriesApiCall(movieOrSeries, searchQuery = query, page = queryPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ movieResult ->

                        if (queryPage == 1) {
                            mView?.hideProgressBar()
                        }
                        mView?.setSearchRecyclerView(movieResult?.moviesList, movieResult?.totalResults!!)
                    }, { error ->
                        error.printStackTrace()
                        mView?.showMessage(error.localizedMessage)
                    })
        }
    }

    override fun requestSearchTypeDialog() {
        mView?.showSearchTypeDialog()
    }
}