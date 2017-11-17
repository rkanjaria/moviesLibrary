package com.example.mf.moviebox.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.mf.moviebox.classes.KeyboardUtils;

/**
 * Created by MF on 17-11-2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void finishActivty(){
        hideKeyboard();
        finish();
    }

    private void hideKeyboard() {
        KeyboardUtils.hideSoftInputKeyboard(this);
    }

}
