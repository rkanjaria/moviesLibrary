package com.example.mf.movielibrary.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.mf.movielibrary.R
import files.backdropUrl
import files.loadImage
import kotlinx.android.synthetic.main.image_layout.view.*


class ImageAdapter(context: Context, val imageList: List<String?>, val centerCrop: Boolean = false) : PagerAdapter() {
    var layoutInfalter: LayoutInflater

    init {
        layoutInfalter = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view === `object` as LinearLayout
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val itemView = layoutInfalter.inflate(R.layout.image_layout, container, false)
        itemView.mainImage.loadImage(backdropUrl + imageList.get(position), R.color.colorPrimaryDark, centerCrop = centerCrop)
        container?.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as LinearLayout)
    }
}
