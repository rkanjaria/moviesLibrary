package com.example.mf.movielibrary.activities.reviewscreen

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.ReviewAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.reviewmodels.UserReview
import files.PARCELABLE_OBJECT
import kotlinx.android.synthetic.main.activity_review.*
import java.lang.reflect.Array
import java.util.*

class ReviewActivity : BaseActivity<ReviewActivityContract.ReviewView, ReviewActivityPresenter>(), ReviewActivityContract.ReviewView {

    override var mPresenter = ReviewActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        initToolbar(toolbar as Toolbar, true, "Reviews")
        mPresenter.requestReviewRecyclerview()
    }

    override fun setReviewRecylerview() {

        val reviewList = intent?.getParcelableArrayListExtra<UserReview>(PARCELABLE_OBJECT) as List<UserReview>

        reviewRecylerview.setHasFixedSize(true)
        reviewRecylerview.layoutManager = LinearLayoutManager(this)
        reviewRecylerview.adapter = ReviewAdapter(reviewList, false)
    }
}
