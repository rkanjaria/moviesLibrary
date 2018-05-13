package com.example.mf.movielibrary.adapters

import android.content.Intent
import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.activities.collectionscreen.CollectionsActivity
import files.*
import kotlinx.android.synthetic.main.collection_list_layout.view.*

/**
 * Created by RK on 10-05-2018.
 */
class CollectionsListAdapter(val collectionIdList: List<Int>, val collectionNames: List<String>,
                             val collectionImages: List<Int>) :
        RecyclerView.Adapter<CollectionsListAdapter.CollectionsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsListViewHolder {
        return CollectionsListViewHolder(parent.inflate(R.layout.collection_list_layout, false))
    }

    override fun getItemCount() = collectionIdList.size

    override fun onBindViewHolder(holder: CollectionsListViewHolder?, position: Int) {
        holder?.bindItems(collectionIdList[position], collectionNames[position], collectionImages[position])
    }

    class CollectionsListViewHolder(collectionView: View) : RecyclerView.ViewHolder(collectionView) {

        val view = collectionView

        fun bindItems(collectionId: Int, collectionName: String, collectionImage : Int) {
            view.collectionName.text = collectionName
            view.collectionImage.loadDrawableImage(collectionImage, R.color.darkGrey)

            view.setOnClickListener({
                val collectionsListIntent = Intent(view.context, CollectionsActivity::class.java)
                collectionsListIntent.putExtra(INT_ID, collectionId)
                collectionsListIntent.putExtra(NAME, collectionName)
                collectionsListIntent.putExtra(BACKDROP_PATH, collectionImage)
                view.context.startActivity(collectionsListIntent)
            })
        }
    }
}