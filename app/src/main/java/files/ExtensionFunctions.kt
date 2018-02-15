package files

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.Px
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.classes.BlurTransform
import com.example.mf.movielibrary.helpers.DatabaseHelper
import org.jetbrains.anko.collections.forEachByIndex
import org.jetbrains.anko.displayMetrics

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

fun TextView.expandOrCollapseTextView() {
    val collapsedMaxLines = 5
    val animation = ObjectAnimator.ofInt(this, "maxLines",
            if (this.maxLines == collapsedMaxLines) this.lineCount else collapsedMaxLines)
    animation.setDuration(250)
            .start()
}

fun LinearLayout.asTagsLayout(tagsList: List<String>) {

    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, context.displayMetrics).toInt()
    var previousTagWidth = 0
    var previousTagHeight = 0
    var left = 0
    var top = 0

    val displayMetrices = DisplayMetrics()
    (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrices)
    val screenWidth = displayMetrices.widthPixels

    val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val tagLayout = RelativeLayout(context)
    tagLayout.setPadding(px, px * 2, px, px * 2)
    tagLayout.layoutParams = params

    tagsList.forEach {
        val tagView = TextView(context)
        tagView.setPadding(px * 2, px, px * 2, px)
        tagView.text = tagsList.get(tagsList.indexOf(it))
        tagView.background = ContextCompat.getDrawable(context, R.drawable.tag_background_drawable)
        tagView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        tagView.typeface = ResourcesCompat.getFont(context, R.font.noto_sans_regular)
        tagView.textSize = 12f
        tagView.maxLines = 1
        tagView.gravity = Gravity.CENTER
        tagView.ellipsize = TextUtils.TruncateAt.END

        val tagViewParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        left = left + previousTagWidth

        if (((screenWidth - px * 2) - left) < tagView.measureTextViewWidth()) {
            top = top + previousTagHeight
            left = 0
        }

        tagViewParams.setMargins(left, top, 0, 0)
        tagView.layoutParams = tagViewParams
        tagLayout.addView(tagView)

        previousTagWidth = tagView.measureTextViewWidth() + px
        previousTagHeight = tagView.measureTextViewHeight() + px
    }

    this.addView(tagLayout)
}

fun TextView.measureTextViewWidth(): Int {
    this.measure(0, 0)
    return this.measuredWidth
}

fun TextView.measureTextViewHeight(): Int {
    this.measure(0, 0)
    return this.measuredHeight
}

/*
fun View.findSelectedTag(context: Context) : String {

    var selectedText = ""

    try {
        if (this is ViewGroup) {
            val viewGroup = this
            for (i in 0 until viewGroup.childCount) {
                val child = viewGroup.getChildAt(i)
                child.findSelectedTag(context)
            }
        }else if(this is TextView){

            this.setOnClickListener {

                if(this.text.equals()){
                    this.background = ContextCompat.getDrawable(context, R.drawable.tag_background_colored_drawable)
                    this.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    selectedText = this.text.toString()
                }else{
                    this.background = ContextCompat.getDrawable(context, R.drawable.tag_background_drawable)
                    this.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                }
            }
        }

    } catch (e: Throwable) {
        e.printStackTrace()
    }
    return selectedText
}*/
