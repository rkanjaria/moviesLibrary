package com.example.mf.movielibrary.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.actormodel.Actor
import files.inflate
import files.loadImage
import files.photoUrl
import kotlinx.android.synthetic.main.actor_recycler_layout.view.*

class ActorsAdapter(val actorsList: List<Actor?>, actorsAdapterListener: ActorsAdapterListener?)
    : RecyclerView.Adapter<ActorsAdapter.ActorViewHolder>() {

    val ACTOR_VIEW = 1
    val LOADER_VIEW = 2

    private var isMoreDataAvailable = true
    private var isLoading = false

    private val mActorsAdapterListener = actorsAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder? {

        when (viewType) {
            ACTOR_VIEW -> return ActorViewHolder(parent.inflate(R.layout.actor_recycler_layout, false))
            LOADER_VIEW -> return LoaderViewHolder(parent.inflate(R.layout.loader_recycler_layout, false))
            else -> return null
        }
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {

        if (position >= itemCount - 1 && !isLoading && isMoreDataAvailable) {
            isLoading = true
            mActorsAdapterListener?.loadMoreActors()
        }

        if (getItemViewType(position) == ACTOR_VIEW) {
            holder.bindViews(actorsList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {

        if (actorsList.get(position) == null) {
            return LOADER_VIEW
        } else {
            return ACTOR_VIEW
        }
    }

    override fun getItemCount(): Int = actorsList.size

    inner class LoaderViewHolder(loaderView: View) : ActorViewHolder(loaderView)

    inner open class ActorViewHolder(actorsView: View) : RecyclerView.ViewHolder(actorsView) {

        private var view: View = actorsView

        fun bindViews(actorModel: Actor?) {
            view.actorImage.loadImage(photoUrl + actorModel?.profileImage)
            view.actorName.text = actorModel?.actorName

            view.setOnClickListener { mActorsAdapterListener?.onActorClicked(actorModel) }
        }
    }

    interface ActorsAdapterListener {
        fun loadMoreActors() {}
        fun onActorClicked(actorModel: Actor?) {}
    }

    fun refreshAdapter(lastPosition: Int) {
        notifyItemRangeChanged(lastPosition, actorsList.size)
        isLoading = false
    }
}