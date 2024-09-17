package com.example.narasimha.androidtestapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.narasimha.androidtestapp.model.Manufacturer

class ManufacturerDetailsViewModel : ViewModel() {

    private val _manufacturer = MutableLiveData<Manufacturer>()
    val vehiclesList: LiveData<Manufacturer> get() = _manufacturer

    fun setVehiclesList(manufacturer: Manufacturer) {
        _manufacturer.value = manufacturer
    }

}