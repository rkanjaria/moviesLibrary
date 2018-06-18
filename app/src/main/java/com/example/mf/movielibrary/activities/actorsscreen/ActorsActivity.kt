package com.example.mf.movielibrary.activities.actorsscreen

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.actormodel.Actor
import com.example.mf.movielibrary.models.actormodel.ActorIdResult
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.activity_actors.*

class ActorsActivity : BaseActivity<ActorsActivityContract.ActorsView, ActorsActivityPresenter>(),
        ActorsActivityContract.ActorsView, MovieRecyclerAdapter.OnMovieSeriesAdapterListener {

    private lateinit var actorModel: Actor
    override var mPresenter = ActorsActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors)

        initToolbar(toolbar as Toolbar, true, intent.getStringExtra(NAME))
        mPresenter.callGetActorApi(intent.getIntExtra(INT_ID, -1))
        mPresenter.callGetActorIds(intent.getIntExtra(INT_ID, -1))
    }

    override fun setActorsData(actor: Actor) {
        actorModel = actor
        mainLayout.visibility = View.VISIBLE
        actorName.text = actor.actorName
        actorBirthday.text = getDateWithCustomFormat(actor.birthday)
        actorBirthPlace.text = actor.placeOfBirth
        actorImage.loadImage(photoUrl + actor.profileImage, R.color.darkGrey)
        actorBiography.text = actor.biography

        mPresenter.callGetActorsMoviesOrSeriesApi(actor.actorId)
        posterCard.setOnClickListener { mPresenter.launchImagesActivity(actor.actorId) }
    }

    override fun showProgressLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressLoading() {
        progressBar.visibility = View.GONE
    }

    override fun setActorMoviesRecyclerview(moviesList: List<Movie?>) {
        actorMovieRecyclerview.setHasFixedSize(true)
        actorMovieRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        actorMovieRecyclerview.adapter = MovieRecyclerAdapter(moviesList, this, true)
        actorMovieRecyclerview.visibility = View.VISIBLE
        knowForTitle.visibility = View.VISIBLE
        viewMore.visibility = View.VISIBLE
        viewMore.setOnClickListener { mPresenter.launchActorsMoviesSeriesActivity(actorModel) }
    }

    override fun setSocialMediaIcons(actorIdResult: ActorIdResult?) {

        if (actorIdResult?.instagramId != null && actorIdResult.instagramId.isNotEmpty()) {
            instaIcon.visibility = View.VISIBLE
            instaIcon.setOnClickListener { mPresenter.openInstagramIntent(actorIdResult.instagramId) }
        }

        if(actorIdResult?.twitterId != null && actorIdResult.twitterId.isNotEmpty()){
            twitterIcon.visibility = View.VISIBLE
            twitterIcon.setOnClickListener { mPresenter.openTwitterIntent(actorIdResult.twitterId) }
        }
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        mPresenter.launchMovieSeriesActivity(movieModel)
    }

}
