package com.example.graphql

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException

class GraphQLClient {

    private val client = OkHttpClient()
    private val baseUrl = "http://13.214.218.226:8000/graphql/"

    fun executeQuery(
        query: String,
        variables: Map<String, Any>,
        callback: (String?, Exception?) -> Unit
    ) {
        val requestBody = buildRequestBody(query, variables)
        val request = buildRequest(requestBody)

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                callback(responseBody, null)
            }

            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }
        })
    }


    private fun buildRequestBody(query: String, variables: Map<String, Any>): RequestBody {
        val json = """
            {
                "query": "$query",
                "variables": ${variables.toJsonString()}
            }
        """.trimIndent()
        return json.toRequestBody("application/json".toMediaType())
    }

    private fun buildRequest(requestBody: RequestBody): Request {
        return Request.Builder()
            .url(baseUrl)
            .post(requestBody)
            .build()
    }

    // Extension function to convert Map<String, Any> to JSON string
    private fun Map<String, Any>.toJsonString(): String {
        val entries = this.entries.joinToString(separator = ",") { (key, value) ->
            "\"$key\":${toJsonValue(value)}"
        }
        return "{$entries}"
    }

    // Function to convert Any to JSON-compatible value
    private fun toJsonValue(value: Any): String {
        return when (value) {
            is String -> "\"$value\""
            is Number -> value.toString()
            is Boolean -> value.toString()
            is Map<*, *> -> (value as Map<String, Any>).toJsonString()
            else -> throw IllegalArgumentException("Unsupported type: ${value::class.java}")
        }
    }

    fun main() {
        val graphQLClient = GraphQLClient()

        // Example: Fetch countries
        val getCountriesQuery = """query GetCountries(${'$'}keyword: String) {
    getCountries(keyword: ${'$'}keyword) {
        id
        name
        flag
        iso_code
        dial_code
    }
}"""
        val getCountriesVariables = mapOf("keyword" to "")
        graphQLClient.executeQuery(
            getCountriesQuery,
            getCountriesVariables
        ) { countriesResponse, countriesException ->
            if (countriesResponse != null) {
                // Handle countries response
                println("Countries Response: $countriesResponse")
            } else {
                // Handle error
                countriesException?.printStackTrace()
            }
        }

        // Example: Fetch states for a selected country
        val getStatesQuery = """query GetStates(${'$'}countryId: Int, ${'$'}keyword: String) {
    getStates(countryId: ${'$'}countryId, keyword: ${'$'}keyword) {
        name
        id
    }
}"""

        val getStatesVariables = mapOf("countryId" to 14, "keyword" to "")
        graphQLClient.executeQuery(
            getStatesQuery,
            getStatesVariables
        ) { statesResponse, statesException ->
            if (statesResponse != null) {
                // Handle states response
                println("States Response: $statesResponse")
            } else {
                // Handle error
                statesException?.printStackTrace()
            }
        }

        // Example: Fetch cities for a selected state
        val getCitiesQuery = """query GetCities(${'$'}keyword: String, ${'$'}stateId: Int) {
    getCities(keyword: ${'$'}keyword, stateId: ${'$'}stateId) {
        name
        id
    }
}"""

        val getCitiesVariables = mapOf("keyword" to "", "stateId" to 1)
        graphQLClient.executeQuery(
            getCitiesQuery,
            getCitiesVariables
        ) { citiesResponse, citiesException ->
            if (citiesResponse != null) {
                // Handle cities response
                println("Cities Response: $citiesResponse")
            } else {
                // Handle error
                citiesException?.printStackTrace()
            }

        }
    }

}