package com.example.weatkertask.data.Retrofit

import com.example.weatkertask.common.Constants
import com.example.weatkertask.data.Retrofit.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitInstance @Inject constructor(private val okHttpClient: OkHttpClientClass) {

    fun getApi() : ApiService =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.client(okHttpClient.getClient())
            .build()
            .create(ApiService::class.java)
}