package com.example.mf.movielibrary.activities.actorsscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.actormodel.Actor

class ActorsActivityContract {

    interface ActorsView : BaseView {
        fun setActorsData(actor: Actor)
        fun showProgressLoading()
        fun hideProgressLoading()
    }

    interface ActorsPresenter : BasePresenter<ActorsView> {
        fun callGetActorApi(actorId: Int)
    }
}