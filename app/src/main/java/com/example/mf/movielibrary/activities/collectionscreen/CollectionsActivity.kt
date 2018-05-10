package com.example.mf.movielibrary.activities.collectionscreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.base.BaseActivity
import files.INT_ID
import files.NAME
import kotlinx.android.synthetic.main.activity_collections.*

class CollectionsActivity : BaseActivity<CollectionsActivityContract.CollectionsView, CollectionsActivityPresenter>(),
        CollectionsActivityContract.CollectionsView {

    override var mPresenter = CollectionsActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)

        initToolbar(toolbar as Toolbar, true, intent.getStringExtra(NAME))
        mPresenter.callGetListApi(intent.getIntExtra(INT_ID, 0))
    }
}
