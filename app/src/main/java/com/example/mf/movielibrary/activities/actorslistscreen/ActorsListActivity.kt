package com.example.mf.movielibrary.activities.actorslistscreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_actors_list.*

class ActorsListActivity : BaseActivity<ActorsListActivityContract.ActorsListView, ActorsListActivityPresenter>(), ActorsListActivityContract.ActorsListView {

    override var mPresenter = ActorsListActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors_list)
        initToolbar(toolbar as Toolbar, true, "Actors")
    }
}
