package com.example.mf.movielibrary.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.TypedValue
import org.jetbrains.anko.displayMetrics
import android.view.LayoutInflater
import com.example.mf.movielibrary.R


/**
 * Created by RK on 02-02-2018.
 */
class MoviesCardView : CardView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec , widthMeasureSpec +
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, context.displayMetrics).toInt())
    }

    /*private fun initialize(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.card, this)
        // ImageView imageView = (ImageView)getView.findViewById(...);
    }*/
}