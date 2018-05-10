package com.example.mf.movielibrary.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.activities.collectionscreen.CollectionsActivity
import files.INT_ID
import files.NAME
import files.inflate
import kotlinx.android.synthetic.main.collection_list_layout.view.*

/**
 * Created by RK on 10-05-2018.
 */
class CollectionsListAdapter(val collectionIdList: List<Int>, val collectionNames: List<String>) :
        RecyclerView.Adapter<CollectionsListAdapter.CollectionsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsListViewHolder {
        return CollectionsListViewHolder(parent.inflate(R.layout.collection_list_layout, false))
    }

    override fun getItemCount() = collectionIdList.size

    override fun onBindViewHolder(holder: CollectionsListViewHolder?, position: Int) {
        holder?.bindItems(collectionIdList[position], collectionNames[position])
    }

    class CollectionsListViewHolder(collectionView: View) : RecyclerView.ViewHolder(collectionView) {

        val view = collectionView

        fun bindItems(collectionId: Int, collectionName: String) {
            view.collectionName.text = collectionName

            view.setOnClickListener({
                val collectionsListIntent = Intent(view?.context, CollectionsActivity::class.java)
                collectionsListIntent.putExtra(INT_ID, collectionId)
                collectionsListIntent.putExtra(NAME, collectionName)
                view.context.startActivity(collectionsListIntent)
            })
        }
    }
}