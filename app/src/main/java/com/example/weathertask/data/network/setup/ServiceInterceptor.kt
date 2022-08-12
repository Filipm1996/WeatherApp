package com.example.weathertask.data.network.setup

import com.example.weathertask.common.Constants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class   ServiceInterceptor() :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url: HttpUrl = request.url().newBuilder().addQueryParameter("appid", Constants.apiKey).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}