package com.example.graphql

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NetworkService {
        @Headers("Content-Type: application/json")
        @POST("/graphql")
        suspend fun getCountries(@Body request: CountryRequest): Response<CountryResponse>

        @Headers("Content-Type: application/json")
        @POST("/graphql")
        suspend fun getStates(@Body request: StateRequest): Response<StateResponse>

        @Headers("Content-Type: application/json")
        @POST("/graphql")
        suspend fun getCities(@Body request: CityRequest): Response<CityResponse>
}