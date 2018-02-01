package com.example.mf.movielibrary.activities.imgescreen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.base.BaseActivity

class ImageActivity : BaseActivity<ImageActivityContract.ImageBaseView, ImageActivityPresenter>(), ImageActivityContract.ImageBaseView {

    override var mPresenter = ImageActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
    }
}
