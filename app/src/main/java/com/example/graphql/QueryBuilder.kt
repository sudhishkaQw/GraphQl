package com.example.graphql

object QueryBuilder {
    fun getCountriesQuery(): String {
        return """
            query {
                countries {
                    id
                    name
                    flag
                    iso_code
                    dial_code
                }
            }
        """.trimIndent()
    }

    fun getStatesQuery(countryId: Int): String {
        return """
            query GetStates($countryId: Int) {
                getStates(countryId: $countryId) {
                    id
                    name
                }
            }
        """.trimIndent()
    }

    fun getCitiesQuery(stateId: Int): String {
        return """
            query GetCities($stateId: Int) {
                getCities(stateId: $stateId) {
                    id
                    name
                }
            }
        """.trimIndent()
    }
}