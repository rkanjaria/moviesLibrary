package com.example.mf.moviespace.activities.homescreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.mf.moviespace.R
import com.example.mf.moviespace.base.BaseActivity

/**
 * Created by MF on 28-11-2017.
 */
class HomeActivity : BaseActivity<HomeActivityContract.View, HomeActivityPresenter>(),
        HomeActivityContract.View{

    lateinit var toolbar : Toolbar

    override fun showRequestedMessage(str: String) {
        showMessage(str)
    }

    override var mPresenter: HomeActivityPresenter = HomeActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar = findViewById(R.id.toolbar)

        mPresenter.showToast("Hello User")
        initToolbar(toolbar , true, "Movies")

    }
}