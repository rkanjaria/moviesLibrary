package com.example.mf.movielibrary.activities.collectionscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

/**
 * Created by RK on 10-05-2018.
 */
class CollectionsActivityContract {

    interface CollectionsView : BaseView {}

    interface CollectionsPresenter : BasePresenter<CollectionsView> {
        fun callGetListApi(intExtra: Int)
    }
}