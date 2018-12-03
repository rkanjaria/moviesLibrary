package com.example.mf.movielibrary.activities.castscreen

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.CastRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.castmodel.Cast
import files.PARCELABLE_OBJECT
import files.runLayoutAnimation
import kotlinx.android.synthetic.main.activity_cast.*

class CastActivity : BaseActivity<CastActivityContract.CastView, CastActivityPresenter>(),
        CastActivityContract.CastView, CastRecyclerAdapter.OnCastAdapterListener {

    override var mPresenter = CastActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast)
        initToolbar(toolbar as Toolbar, true, "Cast")
        mPresenter.requestCasrRecyclerview()
    }

    override fun setCastRecyclerView() {
        val castList = intent?.getParcelableArrayListExtra<Cast>(PARCELABLE_OBJECT) as List<Cast>
        castRecyclerview.setHasFixedSize(true)
        castRecyclerview.layoutManager = GridLayoutManager(this, 3)
        castRecyclerview.adapter = CastRecyclerAdapter(castList, this, false)
        runLayoutAnimation(castRecyclerview, R.anim.grid_layout_animation_fall_down)
    }

    override fun onCastClicked(castModel: Cast) {
        mPresenter.launchActorsActivity(castModel)
    }
}
