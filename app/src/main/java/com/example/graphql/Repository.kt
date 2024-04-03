package com.example.graphql

import retrofit2.Response

class Repository(private val networkService: NetworkService) {
    suspend fun getCountries(keyword: String): Response<CountryResponse> {
        val request = CountryRequest(keyword)
        return networkService.getCountries(request)
    }

    suspend fun getStates(countryId: Int, keyword: String): Response<StateResponse> {
        val request = StateRequest(countryId, keyword)
        return networkService.getStates(request)
    }

    suspend fun getCities(stateId: Int, keyword: String): Response<CityResponse> {
        val request = CityRequest(stateId, keyword)
        return networkService.getCities(request)
    }
}