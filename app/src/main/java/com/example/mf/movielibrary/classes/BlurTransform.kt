package com.example.mf.movielibrary.classes

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * Created by RK on 19-01-2018.
 */
class BlurTransform(context: Context) : BitmapTransformation() {

    val renderScript = RenderScript.create(context)

    override fun updateDiskCacheKey(messageDigest: MessageDigest?) {
        messageDigest?.update("BlurTransform".toByteArray())
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {

        val bluredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true)
        val input = Allocation.createFromBitmap(renderScript, bluredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED)
        val output = Allocation.createTyped(renderScript, input.type)

        val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        scriptIntrinsicBlur.setInput(input)
        scriptIntrinsicBlur.setRadius(10f)
        scriptIntrinsicBlur.forEach(output)
        output.copyTo(bluredBitmap)

        return bluredBitmap
    }
}