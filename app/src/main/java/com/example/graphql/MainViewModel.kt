package com.example.graphql

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _countriesLiveData = MutableLiveData<List<Country>>()
    val countriesLiveData: LiveData<List<Country>> get() = _countriesLiveData

    private val _statesLiveData = MutableLiveData<List<State>>()
    val statesLiveData: LiveData<List<State>> get() = _statesLiveData

    private val _citiesLiveData = MutableLiveData<List<City>>()
    val citiesLiveData: LiveData<List<City>> get() = _citiesLiveData

    fun getCountries(keyword: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCountries(keyword)
                if (response.isSuccessful) {
                    val countryResponse = response.body()
                    if (countryResponse != null) {

                        Log.d("CountryResponse", countryResponse.toString())
                        _countriesLiveData.value = countryResponse.countries
                    }
                } else {
                    // Log the error response
                    Log.e("CountryError", response.errorBody()?.string() ?: "Unknown error")
                }
            } catch (e: Exception) {
                // Log any exceptions
                Log.e("CountryException", "Exception: ${e.message}", e)
            }
        }
    }
    fun getStates(countryId: Int, keyword: String) {
        viewModelScope.launch {
            val response = repository.getStates(countryId, keyword)
            if (response.isSuccessful) {
                _statesLiveData.value = response.body()?.states
            } else {
                Log.e("API_ERROR", "Failed to fetch states: ${response.message()}")
            }
        }
    }

    fun getCities(stateId: Int, keyword: String) {
        viewModelScope.launch {
            val response = repository.getCities(stateId, keyword)
            if (response.isSuccessful) {
                _citiesLiveData.value = response.body()?.cities
            } else {
                Log.e("API_ERROR", "Failed to fetch states: ${response.message()}")
            }
        }
    }

    // ViewModel Factory
    class Factory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}