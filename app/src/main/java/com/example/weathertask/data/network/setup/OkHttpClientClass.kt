package com.example.weathertask.data.network.setup

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