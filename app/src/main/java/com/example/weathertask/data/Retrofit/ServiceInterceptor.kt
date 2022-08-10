package com.example.weathertask.data.Retrofit

import com.example.weathertask.common.Constants
import okhttp3.Interceptor
import okhttp3.Response


class ServiceInterceptor() :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val finalToken = "Bearer " + Constants.apiKey
        request = request.newBuilder()
            .addHeader("Authorization", finalToken)
            .build()

        return chain.proceed(request)
    }

}