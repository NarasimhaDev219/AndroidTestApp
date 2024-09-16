package com.example.narasimha.androidtestapp.model

data class Manufacturer(
    val Country: String,
    val Mfr_CommonName: String?,
    val Mfr_ID: Int,
    val Mfr_Name: String,
    val VehicleTypes: List<VehicleType>
)