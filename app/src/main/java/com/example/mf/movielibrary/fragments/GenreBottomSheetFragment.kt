package com.example.mf.movielibrary.fragments


import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.genremodel.Genre
import files.MOVIE
import files.MOVIE_OR_SERIES
import files.TV_SHOWS
import files.database
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*
import org.jetbrains.anko.displayMetrics


/**
 * A simple [Fragment] subclass.
 */
class GenreBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var rootView: View
    private var mListener: GenreBottomSheetListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        rootView = inflater!!.inflate(R.layout.bottom_sheet_layout, container, false)
        initFragment()
        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is GenreBottomSheetListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() +
                    "must implement GenreBottomSheetListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun initFragment() {

        val moviesOrSeries = activity.intent.getStringExtra(MOVIE_OR_SERIES)
        createTagsLayout(moviesOrSeries)

        if (moviesOrSeries == MOVIE) {
            selectTag(rootView.moviesTag)
            deselectTag(rootView.tvShowsTag)
        } else {
            selectTag(rootView.tvShowsTag)
            deselectTag(rootView.moviesTag)
        }

        rootView.moviesTag.setOnClickListener {
            mListener?.onMovieOrSeriesClicked(MOVIE)
            selectTag(rootView.moviesTag)
            deselectTag(rootView.tvShowsTag)
        }
        rootView.tvShowsTag.setOnClickListener {
            mListener?.onMovieOrSeriesClicked(TV_SHOWS)
            selectTag(rootView.tvShowsTag)
            deselectTag(rootView.moviesTag)
        }
    }

    fun createTagsLayout(moviesOrSeries: String) {

        val tagsList = context.database.getAllGenres(moviesOrSeries)

        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, activity.displayMetrics).toInt()
        val px6dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6f, activity.displayMetrics).toInt()
        var previousTagWidth = 0
        var previousTagHeight = 0
        var left = 0
        var top = 0

        val displayMetrices = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrices)
        val screenWidth = displayMetrices.widthPixels

        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val tagLayout = RelativeLayout(context)
        tagLayout.setTag("TagLayout")
        tagLayout.setPadding(px, px * 2, px, px * 2)
        tagLayout.layoutParams = params

        tagsList.forEach {
            val tagView = TextView(context)
            tagView.setPadding(px * 2, px6dp, px * 2, px6dp)
            tagView.text = tagsList.get(tagsList.indexOf(it)).genreName
            tagView.tag = tagsList.get(tagsList.indexOf(it)).genreId
            tagView.background = ContextCompat.getDrawable(context, R.drawable.tag_background_drawable)
            tagView.setTextColor(ContextCompat.getColor(context, R.color.mediumGrey))
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

            tagView.setOnClickListener {
                mListener?.onGenreTagsClicked(this.tag)
                selectTag(tagView)
            }

            tagLayout.addView(tagView)

            previousTagWidth = tagView.measureTextViewWidth() + px
            previousTagHeight = tagView.measureTextViewHeight() + px
        }

        if (rootView.genreTagsLayout.childCount > 0) {
            rootView.genreTagsLayout.removeAllViews()
        }
        rootView.genreTagsLayout.addView(tagLayout)
    }

    fun TextView.measureTextViewWidth(): Int {
        this.measure(0, 0)
        return this.measuredWidth
    }

    fun TextView.measureTextViewHeight(): Int {
        this.measure(0, 0)
        return this.measuredHeight
    }


    fun selectTag(tagToBeSelected: TextView) {
        tagToBeSelected.background = ContextCompat.getDrawable(context, R.drawable.tag_background_colored_drawable)
        tagToBeSelected.setTextColor(ContextCompat.getColor(context, R.color.darkGrey))
    }

    fun deselectTag(tagToBeDeselected: TextView) {
        tagToBeDeselected.background = ContextCompat.getDrawable(context, R.drawable.tag_background_drawable)
        tagToBeDeselected.setTextColor(ContextCompat.getColor(context, R.color.mediumGrey))
    }

    interface GenreBottomSheetListener {

        fun onMovieOrSeriesClicked(moviesOrSeries: String)
        fun onGenreTagsClicked(tag: String)
    }


}
