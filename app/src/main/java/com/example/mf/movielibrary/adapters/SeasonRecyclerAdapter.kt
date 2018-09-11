package com.example.mf.movielibrary.adapters

import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.movieseriesdetailsmodel.Season
import files.getYearFromDate
import files.inflate
import files.loadImage
import files.photoUrl
import kotlinx.android.synthetic.main.movie_recycler_layout.view.*

/**
 * Created by RK on 05-02-2018.
 */
class SeasonRecyclerAdapter(val seasonList: List<Season>, seasonAdapterListener: SeasonAdapterListener?) : RecyclerView.Adapter<SeasonRecyclerAdapter.SeasonViewHolder>() {

    val seasonListener = seasonAdapterListener;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        return SeasonViewHolder(parent.inflate(R.layout.movie_recycler_layout, false))
    }

    override fun onBindViewHolder(holder: SeasonViewHolder?, position: Int) {
        holder?.bindViews(seasonList.get(position))
    }

    override fun getItemCount(): Int {
        return seasonList.size
    }

    inner class SeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        fun bindViews(seasonModel : Season){

            val widthInDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, view.resources.displayMetrics)
            view.layoutParams.width = widthInDp.toInt()
            view.moviePoster.loadImage(photoUrl + seasonModel.posterPath, R.color.darkGrey)
            //view.movieBottomLayout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.darkGrey))

            view.movieName.text = "Season ${seasonModel.seasonNumber}"
            view.movieYear.text = getYearFromDate(seasonModel.airDate)

            view.setOnClickListener({
                seasonListener?.onSeasonClicked(seasonModel)
            })
        }
    }

    interface SeasonAdapterListener{
        fun onSeasonClicked(season: Season)
    }
}