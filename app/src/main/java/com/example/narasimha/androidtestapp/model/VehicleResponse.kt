package com.example.narasimha.androidtestapp.model

data class VehicleResponse(
    val Count: Int,
    val Message: String,
    val Results: List<Manufacturer>
)