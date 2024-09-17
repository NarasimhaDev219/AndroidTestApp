package com.example.narasimha.androidtestapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.narasimha.androidtestapp.model.Manufacturer
import com.example.narasimha.androidtestapp.network.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {

    private val _manufacturers = MutableLiveData<List<Manufacturer>>()
    val manufacturers: LiveData<List<Manufacturer>> = _manufacturers

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchManufacturers() {

        viewModelScope.launch {
            val result = repository.getManufacturers()
            result.fold(
                onSuccess = { response ->
                    _manufacturers.value = response.Results
                },
                onFailure = { throwable ->
                    _error.value = throwable.message
                }
            )
        }
    }
}
