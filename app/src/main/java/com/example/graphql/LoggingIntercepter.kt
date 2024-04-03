package com.example.graphql

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class LoggingIntercepter : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val requestString = request.url.toString()
        val responseString = response.body?.string()

        Log.d("Network", "Request: $requestString")
        Log.d("Network", "Response: $responseString")

        return response.newBuilder()
            .body(responseString?.toResponseBody(response.body?.contentType()))
            .build()
    }
}
