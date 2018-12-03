package com.example.mf.movielibrary.activities.collectionlistscreen

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.activities.collectionscreen.CollectionsActivity
import com.example.mf.movielibrary.adapters.CollectionsListAdapter
import com.example.mf.movielibrary.base.BaseActivity
import files.BACKDROP_PATH
import files.INT_ID
import files.NAME
import files.runLayoutAnimation
import android.support.v4.util.Pair
import kotlinx.android.synthetic.main.activity_collections_list.*

class CollectionsListActivity : BaseActivity<CollectionsListActivityContract.CollectionsListView, CollectionsListActivityPresenter>(),
        CollectionsListActivityContract.CollectionsListView, CollectionsListAdapter.CollectionAdapterListsner {

    override fun onClick(collectionId: Int, collectionName: String, collectionImage: Int, sharedImage: View, collectionView: View, collectionNameText: View) {
        val collectionsListIntent = Intent(this, CollectionsActivity::class.java)
        collectionsListIntent.putExtra(INT_ID, collectionId)
        collectionsListIntent.putExtra(NAME, collectionName)
        collectionsListIntent.putExtra(BACKDROP_PATH, collectionImage)
        //startActivity(collectionsListIntent)

        val pairOne = Pair.create(sharedImage, ViewCompat.getTransitionName(sharedImage))
        val pairTwo = Pair.create(collectionView, ViewCompat.getTransitionName(collectionView))
        //val pairThree = Pair.create(collectionNameText, ViewCompat.getTransitionName(collectionNameText))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairOne, pairTwo)
        startActivity(collectionsListIntent, options.toBundle())
    }

    val collectionsIds = listOf(1, 3, 338, 3321, 69937, 15273, 272)
    val collectionsName = listOf("The Marvel Universe",
            "The DC Comics Universe",
            "Disney Classic Collection",
            "Anime Movies",
            "Batman Animated Movies",
            "James Bond Collection",
            "Harry Potter Movies")

    val collectionsImages = listOf(R.drawable.marvel,
            R.drawable.dc,
            R.drawable.disney_one,
            R.drawable.anime,
            R.drawable.batman,
            R.drawable.bond,
            R.drawable.harry_potter)

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
        collectionsListRecylerview.adapter = CollectionsListAdapter(collectionsIds, collectionsName, collectionsImages, this)
        runLayoutAnimation(collectionsListRecylerview, R.anim.layout_animation_fall_down)
    }
}
