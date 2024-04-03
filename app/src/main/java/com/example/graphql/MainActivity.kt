package com.example.graphql

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graphql.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var countriesAdapter: CountriesAdapter
    private lateinit var statesAdapter: StatesAdapter
    private lateinit var citiesAdapter: CitiesAdapter
    private lateinit var repository: Repository
    private lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        networkService = RetrofitClient.create()
        countriesAdapter = CountriesAdapter()
        statesAdapter = StatesAdapter()
        citiesAdapter = CitiesAdapter()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        }
        val countriesRecyclerView: RecyclerView = findViewById(R.id.countriesRecyclerView)
        countriesRecyclerView.adapter = countriesAdapter
        countriesRecyclerView.layoutManager = LinearLayoutManager(this)

        val statesRecyclerView: RecyclerView = findViewById(R.id.statesRecyclerView)
        statesRecyclerView.adapter = statesAdapter
        statesRecyclerView.layoutManager = LinearLayoutManager(this)

        val citiesRecyclerView: RecyclerView = findViewById(R.id.citiesRecyclerView)
        citiesRecyclerView.adapter = citiesAdapter
        citiesRecyclerView.layoutManager = LinearLayoutManager(this)

        repository = Repository(networkService)
        val viewModelFactory = MainViewModel.Factory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.countriesLiveData.observe(this, Observer { countries ->
            countriesAdapter.setData(countries)
        })

        viewModel.statesLiveData.observe(this, Observer { states ->
            statesAdapter.setData(states)
        })

        viewModel.citiesLiveData.observe(this, Observer { cities ->
            citiesAdapter.setData(cities)
        })

        // Fetch initial data
        viewModel.getCountries("")

        // Set item click listeners
        countriesAdapter.setOnItemClickListener { country ->
            viewModel.getStates(country.id, "")
        }

        statesAdapter.setOnItemClickListener { state ->
            viewModel.getCities(state.id, "")
        }

    }
}