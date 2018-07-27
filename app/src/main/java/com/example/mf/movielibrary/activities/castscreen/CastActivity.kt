package com.example.mf.movielibrary.activities.castscreen

import android.os.Bundle
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.base.BaseActivity

class CastActivity : BaseActivity<CastActivityContract.CastView, CastActivityPresenter>(), CastActivityContract.CastView {

    override var mPresenter = CastActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast)
    }
}
