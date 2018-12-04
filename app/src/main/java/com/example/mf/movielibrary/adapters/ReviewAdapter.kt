package com.example.mf.movielibrary.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.reviewmodels.UserReview
import com.example.mf.movielibrary.files.inflate
import com.example.mf.movielibrary.files.loadCircularImage
import kotlinx.android.synthetic.main.review_recyler_layout.view.*

class ReviewAdapter(val reviewList: List<UserReview>, val isFromMovieSeriesActivity: Boolean) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(parent.inflate(R.layout.review_recyler_layout, false))
    }

    override fun getItemCount(): Int {
        if (isFromMovieSeriesActivity) {
            if (reviewList.size < 1) return reviewList.size else return 1
        } else {
            return reviewList.size
        }
    }

    override fun onBindViewHolder(holder: ReviewViewHolder?, position: Int) {
        holder?.bindViews(reviewList[position])
    }

    inner class ReviewViewHolder(reviewItem: View) : RecyclerView.ViewHolder(reviewItem) {
        val view = reviewItem

        fun bindViews(reviewModel: UserReview) {
            view.reviewerImage.loadCircularImage(R.drawable.green_background)
            view.reviewerFirstLetter.text = reviewModel.author?.get(0).toString().toUpperCase()
            view.reviewerName.text = reviewModel.author
            view.review.text = reviewModel.content
        }
    }
}