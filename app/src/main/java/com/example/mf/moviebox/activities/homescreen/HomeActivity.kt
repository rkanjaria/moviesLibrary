package com.example.mf.moviebox.activities.homescreen

import android.os.Bundle
import com.example.mf.moviebox.R
import com.example.mf.moviebox.base.BaseActivity

/**
 * Created by MF on 28-11-2017.
 */
class HomeActivity : BaseActivity<HomeActivityContract.View, HomeActivityPresenter>(),
        HomeActivityContract.View{

    override fun showRequestedMessage(str: String) {
        showMessage(str)
    }

    override var mPresenter: HomeActivityPresenter = HomeActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mPresenter.showToast("Hello User")

    }
}