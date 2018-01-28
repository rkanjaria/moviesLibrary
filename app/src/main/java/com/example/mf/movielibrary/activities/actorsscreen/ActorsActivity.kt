package com.example.mf.movielibrary.activities.actorsscreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.actormodel.Actor
import files.INT_ID
import files.NAME
import files.loadImage
import files.photoUrl
import kotlinx.android.synthetic.main.activity_actors.*

class ActorsActivity : BaseActivity<ActorsActivityContract.ActorsView, ActorsActivityPresenter>(),
        ActorsActivityContract.ActorsView {

    override var mPresenter = ActorsActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors)

        initToolbar(toolbar as Toolbar, true, intent.getStringExtra(NAME))
        mPresenter.callGetActorApi(intent.getIntExtra(INT_ID, -1))
    }

    override fun setActorsData(actor: Actor) {
        mainLayout.visibility = View.VISIBLE
        actorName.text = actor.actorName
        actorImage.loadImage(photoUrl + actor.profileImage, R.color.darkGrey)
        actorBiography.text = actor.biography
    }

    override fun showProgressLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressLoading() {
        progressBar.visibility = View.GONE
    }
}
