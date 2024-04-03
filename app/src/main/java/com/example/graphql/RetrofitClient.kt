package com.example.graphql

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://13.214.218.226:8000/graphql/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(LoggingIntercepter())
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun create(): GraphQLServices {
        return retrofit.create(GraphQLServices::class.java)
    }
}