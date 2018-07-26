package com.example.mf.movielibrary.activities.actorslistscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.actormodel.Actor

class ActorsListActivityContract {

    interface ActorsListView : BaseView{
        fun setActorsRecyclerview(actorsList: List<Actor>, totalResults: Int)
        fun hideProgressBar()
        fun showProgressBar()
    }
    interface ActorsListPresenter : BasePresenter<ActorsListView>{
        fun callGetPopularPeopleApi(page: Int)
        fun launchActorsActivity(actorModel: Actor?)
        fun lauchchSearchActivity(movieSeriesOrActors: String)
    }
}