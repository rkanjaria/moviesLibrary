package com.example.mf.movielibrary.base

import com.example.mf.movielibrary.RetrofitApiService
import com.example.mf.movielibrary.helpers.RetrofitHelper

/**
 * Created by MF on 05-12-2017.
 */
object ApiRepositoryProvider {

    fun provideApiRepository(): ApiRepository {
        return ApiRepository(RetrofitHelper.getRetroClient.create())
    }
}