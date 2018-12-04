package com.example.mf.movielibrary.files

import android.animation.ObjectAnimator
import android.arch.persistence.room.Room
import android.content.Context
import android.support.annotation.AnimRes
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.classes.BlurTransform
import com.example.mf.movielibrary.database.AppDatabase
import java.text.DecimalFormat

/**
 * Created by RK on 23-12-2017.
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false) =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun ImageView.loadImage(url: String, placeholder: Int = R.color.darkGrey, centerCrop: Boolean = false) {

    if (centerCrop) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions().centerCrop())
                .apply(RequestOptions.placeholderOf(placeholder))
                .apply(RequestOptions.errorOf(placeholder))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
    } else {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.placeholderOf(placeholder))
                .apply(RequestOptions.errorOf(placeholder))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
    }
}

fun ImageView.loadBlurImage(url: String) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions().transform(BlurTransform(context, 20f)))
            .apply(RequestOptions.placeholderOf(R.color.primary_dark_material_dark))
            .apply(RequestOptions.errorOf(R.color.primary_dark_material_dark))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun ImageView.loadCircularImage(url: Any) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun ImageView.loadDrawableImage(@DrawableRes url: Int, placeholder: Int = R.color.darkGrey) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions().centerCrop())
            .apply(RequestOptions.placeholderOf(placeholder))
            .apply(RequestOptions.errorOf(placeholder))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)

}

fun ImageView.loadDrawable(@DrawableRes url: Int) {
    Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

val Context.database: AppDatabase
    get() = Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()

fun TextView.expandOrCollapseTextView() {
    val collapsedMaxLines = 5
    val animation = ObjectAnimator.ofInt(this, "maxLines",
            if (this.maxLines == collapsedMaxLines) this.lineCount else collapsedMaxLines)
    animation.setDuration(250)
            .start()
}

fun formatRating(rating: Double): String = if (rating != 0.0) DecimalFormat("#.#").format(rating) else "No rating"

fun runLayoutAnimation(recyclerView: RecyclerView, @AnimRes animResFile: Int) {

    if (recyclerView.adapter != null) {
        val animationController = AnimationUtils.loadLayoutAnimation(recyclerView.context, animResFile)
        recyclerView.layoutAnimation = animationController
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}