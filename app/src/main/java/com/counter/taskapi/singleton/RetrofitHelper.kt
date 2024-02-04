package com.counter.taskapi.singleton

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl( "https://coded-pets-api-crud.eapi.joincoded.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}