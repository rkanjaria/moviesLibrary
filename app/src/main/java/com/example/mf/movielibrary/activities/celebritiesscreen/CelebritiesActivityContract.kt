package com.example.mf.movielibrary.activities.celebritiesscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.actormodel.Actor

class CelebritiesActivityContract {

    interface CelebritiesView : BaseView{
        fun setActorsRecyclerview(actorsList: List<Actor>, totalResults: Int)
        fun hideProgressBar()
        fun showProgressBar()
    }
    interface CelebritiesPresenter : BasePresenter<CelebritiesView>{
        fun callGetPopularPeopleApi(page: Int)
        fun launchActorsActivity(actorModel: Actor?)
        fun lauchchSearchActivity(movieSeriesOrActors: String)
    }
}