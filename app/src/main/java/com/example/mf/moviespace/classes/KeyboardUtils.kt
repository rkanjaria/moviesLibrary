package com.example.mf.moviespace.classes

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by MF on 29-11-2017.
 */


class KeyboardUtils {
    companion object {

        fun hideSoftInputKeyboard(activity: Activity) {
            var view = activity.currentFocus
            if (view == null) view = View(activity)
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromInputMethod(view.windowToken, 0)
        }
    }

}