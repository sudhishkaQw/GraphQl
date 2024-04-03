package com.example.graphql

import com.squareup.moshi.Json

data class Country(
    val id: Int, val name: String, val flag: String, val isoCode: String, val dialCode: String)

