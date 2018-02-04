package com.example.mf.movielibrary.activities.actorsscreen

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.actormodel.Actor
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.activity_actors.*

class ActorsActivity : BaseActivity<ActorsActivityContract.ActorsView, ActorsActivityPresenter>(),
        ActorsActivityContract.ActorsView , MovieRecyclerAdapter.OnMovieSeriesAdapterListener{

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
        actorGender.text = getGender(actor.gender)
        actorBirthday.text = getDateWithCustomFormat(actor.birthday)
        actorBirthPlace.text = actor.placeOfBirth
        actorImage.loadImage(photoUrl + actor.profileImage, R.color.darkGrey)
        actorBiography.text = actor.biography
        //actorBiography.setOnClickListener { actorBiography.expandOrCollapseTextView() }

        mPresenter.callGetActorsMoviesOrSeriesApi(actor.actorId, intent.getStringExtra(MOVIE_OR_SERIES))
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
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        mPresenter.launchMovieSeriesActivity(movieModel, intent.getStringExtra(MOVIE_OR_SERIES))
    }

}
