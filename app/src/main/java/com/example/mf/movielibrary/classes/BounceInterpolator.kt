package com.example.mf.movielibrary.classes

import android.view.animation.Interpolator

class BounceInterpolator(val amplitude: Double, val frequency: Double) : Interpolator {

    override fun getInterpolation(time: Float): Float {
        return (-1 * Math.pow(Math.E, -time / amplitude) * Math.cos(frequency * time) + 1).toFloat()
    }
}