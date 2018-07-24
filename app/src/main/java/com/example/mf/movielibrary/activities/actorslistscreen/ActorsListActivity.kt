package com.example.mf.movielibrary.activities.actorslistscreen

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.ActorsAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.actormodel.Actor
import kotlinx.android.synthetic.main.activity_actors_list.*

class ActorsListActivity : BaseActivity<ActorsListActivityContract.ActorsListView, ActorsListActivityPresenter>(),
        ActorsListActivityContract.ActorsListView, ActorsAdapter.ActorsAdapterListener {

    private var page = 1
    private var totalResultsCount = -1
    private val mActorsList = mutableListOf<Actor?>()
    private lateinit var actorAdapter: ActorsAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override var mPresenter = ActorsListActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors_list)
        initToolbar(toolbar as Toolbar, true, "Famous People")
        mPresenter.callGetPopularPeopleApi(page)


        gridLayoutManager = GridLayoutManager(this, 3)
        actorsRecyclerView.layoutManager = gridLayoutManager

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (actorAdapter.getItemViewType(position) == actorAdapter.LOADER_VIEW)
                    gridLayoutManager.spanCount else 1
            }
        }
    }

    override fun setActorsRecyclerview(actorsList: List<Actor>, totalResults: Int) {
        totalResultsCount = totalResults

        if (mActorsList.isEmpty()) {
            // for first time when data loads, add items to list and refresh the recyclerview
            mActorsList.addAll(actorsList)
            actorAdapter = ActorsAdapter(mActorsList, this)
            actorsRecyclerView.adapter = actorAdapter
        } else {
            // for the second time remove the loader view and add the data and refresh the recyclerview
            mActorsList.removeAt(mActorsList.size - 1)
            val lastPosition = mActorsList.size
            if (actorsList.isNotEmpty()) {
                mActorsList.addAll(actorsList)
            }
            actorAdapter.refreshAdapter(lastPosition)
        }

    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun loadMoreActors() {
        actorsRecyclerView.post { loadActors() }
    }

    private fun loadActors() {
        page++
        if (mActorsList.size < totalResultsCount) {
            mActorsList.add(null)
            actorAdapter.notifyItemInserted(mActorsList.size - 1)
            mPresenter.callGetPopularPeopleApi(page)
        }
    }

    override fun onActorClicked(actorModel: Actor?) {
        mPresenter.launchActorsActivity(actorModel)
    }
}
