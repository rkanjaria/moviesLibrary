package com.example.mf.movielibrary.helpers

import android.content.Context
import com.example.mf.movielibrary.RetrofitApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by MF on 01-12-2017.
 */
class RetrofitHelper {

    companion object getRetroClient {

        fun create(): RetrofitApiService{

            val BASE_URL = "https://api.themoviedb.org/3/";

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(RetrofitApiService :: class.java)
        }
    }

}