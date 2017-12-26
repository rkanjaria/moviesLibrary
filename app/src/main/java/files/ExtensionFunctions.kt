package files

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by MF on 23-12-2017.
 */

fun ViewGroup.inflate(@LayoutRes layoutRes : Int, attachToRoot : Boolean = false) : View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.loadImage( url : String, attachToRoot : Boolean = false) {
    Glide.with(context)
            .load(url)
            .into(this)
}