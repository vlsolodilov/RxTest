package com.solodilov.rxtest.request

import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CatFactApi{
    @GET("fact")
    fun getRandomFact(): Single<CatFact>
}

object CatFactApiClient {

    private const val BASE_URL = "https://catfact.ninja/"

    val apiClient: CatFactApi by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        return@lazy retrofit.create(CatFactApi::class.java)
    }
}