package com.example.narasimha.androidtestapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.narasimha.androidtestapp.R
import com.example.narasimha.androidtestapp.adapters.VehiclesAdapter
import com.example.narasimha.androidtestapp.databinding.ActivityManufacturerBinding
import com.example.narasimha.androidtestapp.model.Manufacturer
import com.example.narasimha.androidtestapp.viewmodel.ManufacturerDetailsViewModel

class ManufacturerDetailsActivity : AppCompatActivity() {

    lateinit var viewModel: ManufacturerDetailsViewModel
    lateinit var viewBinding: ActivityManufacturerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityManufacturerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this)[ManufacturerDetailsViewModel::class.java]

        val manufacturer = intent.getSerializableExtra("MANUFACTURER") as? Manufacturer

        viewBinding.vehiclesRecyclerview.layoutManager = LinearLayoutManager(this)

        manufacturer?.let {
            viewModel.setVehiclesList(manufacturer)
        }
        viewModel.vehiclesList.observe(this) { data ->
            data?.let {
                viewBinding.mfrNameID.text = it.Mfr_Name
                viewBinding.mfrIdID.text = it.Mfr_ID.toString()
                viewBinding.mfrCommonNameID.text = it.Mfr_CommonName
                // Set up the RecyclerView
                println("+++++++++++++++++++++++++++++++VehicleTypes+++++${it.VehicleTypes.size}")
                if (it.VehicleTypes.isNotEmpty()){
                    viewBinding.vehiclesRecyclerview.adapter = VehiclesAdapter(it.VehicleTypes )
                }else{

                    viewBinding.mfrVehicleTypeText.apply {
                        text = "No vehicles available for this Manufacturer"
                        setTextColor(ContextCompat.getColor(context, R.color.red))
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewBinding.mfrVehicleTypeText.apply {
            text = "MFR Vehicles list "
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }
}