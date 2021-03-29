package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import com.udacity.asteroidradar.Constants

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET



interface AsteroidsService {
    @GET("neo/rest/v1/feed?api_key=CRKiqDFlKwVsxfPLynJhR9AAGhCaDibe4Fad8srx")
    suspend fun getAsteroids(): String
}


object Network {

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val asteroids = retrofit.create(AsteroidsService::class.java)
}

