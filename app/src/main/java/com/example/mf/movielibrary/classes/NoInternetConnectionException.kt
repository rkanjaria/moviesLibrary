package com.example.mf.movielibrary.classes

import com.example.mf.movielibrary.base.BaseView
import files.INDEFINITE

class NoInternetConnectionException(val mView: BaseView?) : Exception() {
    init { mView?.showSnackBar("Aye caveman! Check if you got internet bruv.", "Check again", INDEFINITE) }
}