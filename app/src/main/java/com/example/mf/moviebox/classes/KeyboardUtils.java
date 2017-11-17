package com.example.mf.moviebox.classes;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by MF on 17-11-2017.
 */

public final class KeyboardUtils {

    public KeyboardUtils(){}

    public static void hideSoftInputKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if(view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

}
