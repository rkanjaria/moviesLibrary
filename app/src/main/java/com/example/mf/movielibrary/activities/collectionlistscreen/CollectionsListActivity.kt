package com.example.mf.movielibrary.activities.collectionlistscreen

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.CollectionsListAdapter
import com.example.mf.movielibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collections_list.*

class CollectionsListActivity : BaseActivity<CollectionsListActivityContract.CollectionsListView, CollectionsListActivityPresenter>(),
        CollectionsListActivityContract.CollectionsListView {

    val collectionsIds = listOf(1, 3, 338, 3321, 69937)
    val collectionsName = listOf("The Marvel Universe",
            "The DC Comics Universe",
            "Disney Classic Collection",
            "Anime Movies",
            "Batman Animated Movies")

    val collectionsImages = listOf(R.drawable.marvel,
            R.drawable.dc,
            R.drawable.disney,
            R.drawable.anime_one,
            R.drawable.batman)

    override var mPresenter = CollectionsListActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections_list)

        initToolbar(toolbar as Toolbar, true, "Collections")
        mPresenter.requestCollectionsRecyclerview()
    }

    override fun setCollectionsRecyclerview() {
        collectionsListRecylerview.layoutManager = LinearLayoutManager(this)
        collectionsListRecylerview.setHasFixedSize(true)
        collectionsListRecylerview.adapter = CollectionsListAdapter(collectionsIds, collectionsName, collectionsImages)
    }
}
