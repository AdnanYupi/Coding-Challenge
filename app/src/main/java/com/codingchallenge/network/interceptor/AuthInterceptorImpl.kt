package com.codingchallenge.network.interceptor

import com.codingchallenge.util.AUTH_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptorImpl : AuthInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
        request.addHeader("Authorization", "Bearer $AUTH_TOKEN")
        return chain.proceed(request.build())
    }
}