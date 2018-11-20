package com.example.mf.movielibrary.activities.episodescreen

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.ImageAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.seasonmodels.Episode
import files.*
import kotlinx.android.synthetic.main.activity_episode.*

class EpisodeActivity : BaseActivity<EpisodeActivityContract.EpisodeView, EpisodeActivityPresenter>(),
        EpisodeActivityContract.EpisodeView {

    override var mPresenter = EpisodeActivityPresenter()
    private val imageList = mutableListOf<String>()
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        val episodeModel = intent.getParcelableExtra(PARCELABLE_OBJECT) as Episode
        initToolbar(toolbar as Toolbar, true, "")

        val seriesId = intent.getIntExtra(ID, -1)
        val seasonNumber = intent.getIntExtra(INT_ID, -1)
        mPresenter.callGetEpisodesImageApi(seriesId, seasonNumber, episodeModel.episodeNumber)

        imageList.add(0, episodeModel.stillPath.toString())

        imageAdapter = ImageAdapter(this, imageList, centerCrop = true)
        episodeBackdropImageViewpager.adapter = imageAdapter
        pageIndicator.setViewPager(episodeBackdropImageViewpager)
        episodeName.text = episodeModel.episodeName
        episodeOverview.text = episodeModel.episodeOverview
        episodeAirDate.text = getDateWithCustomFormat(episodeModel.episodeAirDate)
        episodeVoteAverage.text = formatRating(episodeModel.voteAverage)


        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = "Episode " + String.format("%02d", episodeModel.episodeNumber)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    override fun addImagesToList(imageList: List<String>) {

        if (imageList.size > 1) {
            this.imageList.addAll(if (imageList.size < 4) imageList else imageList.subList(0, 3))
            imageAdapter.notifyDataSetChanged()
            pageIndicator.visibility = View.VISIBLE
        }
    }
}
