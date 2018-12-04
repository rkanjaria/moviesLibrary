package com.example.mf.movielibrary.activities.imgescreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.ImageAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.files.ID
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : BaseActivity<ImageActivityContract.ImageBaseView, ImageActivityPresenter>(), ImageActivityContract.ImageBaseView {

    override var mPresenter = ImageActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        mPresenter.callActorsImageApi(intent.getIntExtra(ID, -1))
        initToolbar(toolbar as Toolbar, true, "")
    }

    override fun setImageViewPager(imageList: List<String?>) {
        imageViewPager.adapter = ImageAdapter(this, imageList)
        imageViewPager.visibility = View.VISIBLE
    }

    override fun showProgressLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressLoading() {
        progressBar.visibility = View.GONE
    }
}
