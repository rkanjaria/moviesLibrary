package files

import android.animation.ObjectAnimator
import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.classes.BlurTransform
import com.example.mf.movielibrary.helpers.DatabaseHelper

/**
 * Created by MF on 23-12-2017.
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.loadImage(url: String, placeholder: Int = R.color.darkGrey, attachToRoot: Boolean = false) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions.placeholderOf(placeholder))
            .apply(RequestOptions.errorOf(placeholder))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun ImageView.loadBlurImage(url: String, attachToRoot: Boolean = false) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions().transform(BlurTransform(context)))
            .apply(RequestOptions.placeholderOf(R.color.primary_dark_material_dark))
            .apply(RequestOptions.errorOf(R.color.primary_dark_material_dark))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)

fun TextView.expandOrCollapseTextView(){
    val collapsedMaxLines = 5
    val animation = ObjectAnimator.ofInt(this, "maxLines",
            if (this.maxLines == collapsedMaxLines) this.lineCount else collapsedMaxLines)
    animation.setDuration(250)
            .start()
}