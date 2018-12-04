package com.example.mf.movielibrary.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.files.inflate
import com.example.mf.movielibrary.files.loadDrawableImage
import kotlinx.android.synthetic.main.collection_list_layout.view.*

/**
 * Created by RK on 10-05-2018.
 */
class CollectionsListAdapter(val collectionIdList: List<Int>,
                             val collectionNames: List<String>,
                             val collectionImages: List<Int>, val mListener: CollectionAdapterListsner?) :
        RecyclerView.Adapter<CollectionsListAdapter.CollectionsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsListViewHolder {
        return CollectionsListViewHolder(parent.inflate(R.layout.collection_list_layout, false))
    }

    override fun getItemCount() = collectionIdList.size

    override fun onBindViewHolder(holder: CollectionsListViewHolder?, position: Int) {
        holder?.bindItems(collectionIdList[position], collectionNames[position], collectionImages[position])
    }

    inner open class CollectionsListViewHolder(collectionView: View) : RecyclerView.ViewHolder(collectionView) {

        val view = collectionView

        fun bindItems(collectionId: Int, collectionName: String, collectionImage: Int) {
            view.collectionName.text = collectionName
            view.collectionImage.loadDrawableImage(collectionImage, R.color.darkGrey)
            view.setOnClickListener {
                mListener?.onClick(collectionId, collectionName, collectionImage)
            }
        }
    }

    interface CollectionAdapterListsner {
        fun onClick(collectionId: Int, collectionName: String, collectionImage: Int)
    }
}