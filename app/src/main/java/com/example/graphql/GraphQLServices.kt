package com.example.graphql

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GraphQLServices : NetworkService {
    @POST("getCountries")
    fun getCountries(): Call<List<Country>>

    @POST("getStates")
    override suspend fun getStates(@Body request: StateRequest): Response<StateResponse>

    @POST("getCities")
    fun getCities(@Body request: String): Call<List<City>>
}