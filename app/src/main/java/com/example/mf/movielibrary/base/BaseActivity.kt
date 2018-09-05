package com.example.mf.movielibrary.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.classes.KeyboardUtils
import files.INDEFINITE
import files.LONG
import files.SHORT


/**
 * Created by RK on 28-11-2017.
 */
abstract class BaseActivity<in V : BaseView, T : BasePresenter<V>>
    : AppCompatActivity(), BaseView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    protected abstract var mPresenter: T

    override fun getContext(): Context = this

    override fun showError(error: String?) {
        toast(error)
    }

    override fun showError(stringResId: Int) {
        toast(stringResId)
    }

    override fun showMessage(stringResId: Int) {
        toast(stringResId)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


    override fun initToolbar(toolbar: Toolbar, showBackButton: Boolean, title: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        KeyboardUtils.hideSoftInputKeyboard(this)
    }

    override fun finishActivity() {
        KeyboardUtils.hideSoftInputKeyboard(this)
        finish()
    }

    override fun lauchchActivity(activityToBeLaunched: Intent) {
        KeyboardUtils.hideSoftInputKeyboard(this)
        startActivity(activityToBeLaunched)
    }

    override fun finishActivityAndStartAnotherActivity(activityToBeLaunched: Intent) {
        KeyboardUtils.hideSoftInputKeyboard(this)
        startActivity(activityToBeLaunched)
        finish()
    }

    fun toast(message: String?) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        val toastLayout = toast.getView() as LinearLayout
        val toastTV = toastLayout.getChildAt(0) as TextView
        toastTV.typeface = ResourcesCompat.getFont(this, R.font.noto_sans_regular)
        toastTV.textSize = 14f
        toast.show()
    }

    fun toast(stringResId: Int) {
        val toast = Toast.makeText(this, stringResId, Toast.LENGTH_SHORT)
        val toastLayout = toast.getView() as LinearLayout
        val toastTV = toastLayout.getChildAt(0) as TextView
        toastTV.typeface = ResourcesCompat.getFont(this, R.font.noto_sans_regular)
        toastTV.textSize = 14f
        toast.show()
    }

    override fun showSnackBar(message: String, buttonName: String, snackbarShowTime: String) {
        var time = Snackbar.LENGTH_INDEFINITE
        when (snackbarShowTime) {
            SHORT -> time = Snackbar.LENGTH_SHORT
            LONG -> time = Snackbar.LENGTH_LONG
            INDEFINITE -> time = Snackbar.LENGTH_INDEFINITE
        }
        val noInternetSnackbar = Snackbar.make(findViewById(android.R.id.content), message, time)
        val snackbarText = noInternetSnackbar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        snackbarText.typeface = ResourcesCompat.getFont(this, R.font.noto_sans_regular)
        noInternetSnackbar.setAction(buttonName, this)
        noInternetSnackbar.show()
    }

    override fun onClick(view: View?) {
        mPresenter.requestSnackBarButtonClicked()
    }

    override fun onSnackBarButtonClicked() {
        return
    }
}