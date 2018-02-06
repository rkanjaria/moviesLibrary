package com.example.mf.movielibrary.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.movieseriesdetailsmodel.Season
import com.example.mf.movielibrary.models.seasonmodels.Episode
import files.getYearFromDate
import files.inflate
import files.loadImage
import files.photoUrl
import kotlinx.android.synthetic.main.episode_recyler_layout.view.*

/**
 * Created by MF on 06-02-2018.
 */
class EpisodeRecyclerAdapter(val episodeList: List<Episode>) : RecyclerView.Adapter<EpisodeRecyclerAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(parent.inflate(R.layout.episode_recyler_layout, false))
    }
    override fun onBindViewHolder(holder: EpisodeViewHolder?, position: Int) {
        holder?.bindViews(episodeList.get(position))
    }

    override fun getItemCount() = episodeList.size

    class EpisodeViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
        var view = itemView

        fun bindViews(episodeModel: Episode){

            view.episodePosterImage.loadImage(photoUrl + episodeModel.stillPath)
            view.episodeTitle.text = episodeModel.episodeName
            view.episodeRating.text = episodeModel.voteAverage.toString()
            view.episodeDate.text = getYearFromDate(episodeModel.episodeAirDate)
        }
    }
}