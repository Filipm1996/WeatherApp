package com.example.weathertask.data.network.setup

import com.example.weathertask.utils.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ServiceInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url = request
            .url()
            .newBuilder()
            .addQueryParameter(API_KEY_NAME, Constants.apiKey)
            .build()
        request = request
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }

    companion object {

        private const val API_KEY_NAME = "appid"
    }
}