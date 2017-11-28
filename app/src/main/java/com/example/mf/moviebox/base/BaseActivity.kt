package com.example.mf.moviebox.base

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by MF on 28-11-2017.
 */
abstract class BaseActivity<in V : BaseView, T : BasePresenter<V>>
    : AppCompatActivity(), BaseView {

    protected abstract var mPresenter: T

    override fun getContext(): Context = this

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showError(stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}