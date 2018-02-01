package com.example.mf.movielibrary.activities.imgescreen

import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

/**
 * Created by MF on 01-02-2018.
 */
class ImageActivityContract{

    interface ImageBaseView : BaseView {}
    interface ImagePresenter : BasePresenter<ImageBaseView> {}
}