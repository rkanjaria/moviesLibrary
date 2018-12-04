package com.example.mf.movielibrary.activities.castscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.actorsscreen.ActorsActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.models.castmodel.Cast
import com.example.mf.movielibrary.files.INT_ID
import com.example.mf.movielibrary.files.NAME

class CastActivityPresenter : BasePresenterImpl<CastActivityContract.CastView>(), CastActivityContract.CastPresenter {

    override fun launchActorsActivity(castModel: Cast) {
        val actorIntent = Intent(mView?.getContext(), ActorsActivity::class.java)
        actorIntent.putExtra(INT_ID, castModel.id)
        actorIntent.putExtra(NAME, castModel.name)
        mView?.lauchchActivity(actorIntent)
    }

    override fun requestCasrRecyclerview() {
        mView?.setCastRecyclerView()
    }
}