package com.example.mf.movielibrary.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.castmodel.Cast
import files.inflate
import files.loadImage
import files.photoUrl
import kotlinx.android.synthetic.main.cast_recycler_layout.view.*

class CastRecyclerAdapter(val castList : List<Cast>, onCastAdapterListener: OnCastAdapterListener) : RecyclerView.Adapter<CastRecyclerAdapter.CastViewHolder>() {

    private val castAdapterListener = onCastAdapterListener

    override fun onBindViewHolder(holder: CastViewHolder?, position: Int) {
        holder?.bindViews(castList.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(parent.inflate(R.layout.cast_recycler_layout, false))
    }

    override fun getItemCount() : Int {
        if(castList.size < 8){
            return castList.size
        }else{
            return 8
        }
    }

    inner class CastViewHolder(castView : View) : RecyclerView.ViewHolder(castView), View.OnClickListener {
        override fun onClick(view: View?) {
            castAdapterListener.onCastClicked(castList.get(adapterPosition))
        }

        val view = castView
        fun bindViews(castModel : Cast){

            view.actorsPic.loadImage(photoUrl + castModel.profilePath, R.color.darkGrey, false)
            view.actorsName.text = castModel.name
            view.actorsCharacterName.text = castModel.character
            view.setOnClickListener(this)
        }
    }

    interface OnCastAdapterListener {
        fun onCastClicked(castModel : Cast)
    }
}