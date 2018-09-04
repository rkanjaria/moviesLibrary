package com.example.mf.movielibrary.helpers

import com.example.mf.movielibrary.RetrofitApiService
import files.baseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by RK on 01-12-2017.
 */
class RetrofitHelper {

    companion object getRetroClient {

        fun create(): RetrofitApiService{
            val retrofit = Retrofit.Builder()
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()

            return retrofit.create(RetrofitApiService :: class.java)
        }

        fun getHttpLoggingInterceptor() : HttpLoggingInterceptor {
            val level = HttpLoggingInterceptor.Level.BODY
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = level
            return httpLoggingInterceptor
        }

        fun getOkHttpClient() : OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(getHttpLoggingInterceptor()).build()
    }
}
