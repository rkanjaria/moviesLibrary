package com.example.mf.movielibrary.base

/**
 * Created by MF on 05-12-2017.
 */
object ApiRepositoryProvider {

    fun provideApiRepository(): ApiRepository{
        return ApiRepository()
    }
}