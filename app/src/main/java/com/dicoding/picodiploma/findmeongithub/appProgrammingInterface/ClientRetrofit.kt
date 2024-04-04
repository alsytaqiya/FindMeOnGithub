package com.dicoding.picodiploma.findmeongithub.appProgrammingInterface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientRetrofit {
    private const val BASE_URL = "https://api.github.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instanceApi: ApiInterface = retrofit.create(ApiInterface::class.java)
}