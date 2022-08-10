package com.example.weathertask.data.Retrofit

import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpClientClass @Inject constructor(
    private val serviceInterceptor: ServiceInterceptor
) : OkHttpClient() {

    fun getClient() : OkHttpClient {
        return Builder()
            .addInterceptor(serviceInterceptor).build()
    }

}