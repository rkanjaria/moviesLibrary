package com.example.mf.movielibrary.adapters

import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.castmodel.Cast
import files.inflate
import files.loadImage
import files.photoUrl
import kotlinx.android.synthetic.main.cast_recycler_layout.view.*
import kotlinx.android.synthetic.main.horizontal_footer_layout.view.*

class CastRecyclerAdapter(val castList: List<Cast>, val castAdapterListener: OnCastAdapterListener, val isHorizontal: Boolean = false) :
        RecyclerView.Adapter<CastRecyclerAdapter.MyViewHolder>() {

    private val NORMAL_VIEW = 1
    private val FOOTER_VIEW = 2

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {

        if (holder is CastViewHolder) {
            holder.bindViews(castList[position])
        } else {
            (holder as FooterViewHolder).bindFooter()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        when (viewType) {
            FOOTER_VIEW -> return FooterViewHolder(parent.inflate(R.layout.horizontal_footer_layout, false))
            else -> return CastViewHolder(parent.inflate(R.layout.cast_recycler_layout, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isHorizontal) {
            if (position == 5) return FOOTER_VIEW else return NORMAL_VIEW
        } else {
            return NORMAL_VIEW
        }
    }

    override fun getItemCount(): Int {
        if (isHorizontal) {
            if (castList.size < 5) return castList.size else return 6
        } else {
            return castList.size
        }
    }

    inner open class CastViewHolder(castView: View) : MyViewHolder(castView), View.OnClickListener {
        override fun onClick(view: View?) {
            castAdapterListener.onCastClicked(castList.get(adapterPosition))
        }

        val view = castView
        fun bindViews(castModel: Cast) {

            if (isHorizontal) {
                val widthInDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, view.resources.displayMetrics)
                view.layoutParams.width = widthInDp.toInt()
            }
            view.actorsPic.loadImage(photoUrl + castModel.profilePath, R.color.darkGrey, false)
            view.actorsName.text = castModel.name
            view.actorsCharacterName.text = castModel.character
            view.setOnClickListener(this)
        }
    }

    inner class FooterViewHolder(footerView: View) : MyViewHolder(footerView) {
        val fView = footerView
        fun bindFooter() {
            fView.moreButton.setOnClickListener {
                castAdapterListener.onMoreClicked(castList)
            }
        }
    }

    inner open class MyViewHolder(mainView: View) : RecyclerView.ViewHolder(mainView)

    interface OnCastAdapterListener {
        fun onCastClicked(castModel: Cast)
        fun onMoreClicked(castList: List<Cast>){}
    }
}