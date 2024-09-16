package com.example.narasimha.androidtestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.narasimha.androidtestapp.adapters.ManufacturerAdapter
import com.example.narasimha.androidtestapp.databinding.ActivityMainBinding
import com.example.narasimha.androidtestapp.viewmodel.VehicleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: VehicleViewModel
    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel = ViewModelProvider(this)[VehicleViewModel::class.java]

        // Set up the RecyclerView
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.manufacturers.observe(this) { manufacturers ->
            viewBinding.recyclerView.adapter = ManufacturerAdapter(manufacturers)
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        // Fetch manufacturers when the view is created
        viewModel.fetchManufacturers()

    }
}