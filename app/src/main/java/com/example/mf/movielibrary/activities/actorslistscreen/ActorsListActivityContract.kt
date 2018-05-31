package com.example.mf.movielibrary.activities.actorslistscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

class ActorsListActivityContract {

    interface ActorsListView : BaseView{}
    interface ActorsListPresenter : BasePresenter<ActorsListView>{}
}