package com.example.narasimha.androidtestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.narasimha.androidtestapp.adapters.ManufacturerAdapter
import com.example.narasimha.androidtestapp.databinding.ActivityMainBinding
import com.example.narasimha.androidtestapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.progressbarId.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Set up the RecyclerView
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.manufacturers.observe(this) { manufacturers ->
            viewBinding.progressbarId.visibility = View.GONE
            viewBinding.recyclerView.adapter = ManufacturerAdapter(manufacturers,this)
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