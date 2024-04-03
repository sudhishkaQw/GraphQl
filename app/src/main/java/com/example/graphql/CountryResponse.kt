package com.example.graphql

import com.squareup.moshi.Json

data class CountryResponse(
    val countries: List<Country>
)
