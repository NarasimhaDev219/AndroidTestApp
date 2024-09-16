package com.example.narasimha.androidtestapp.network

import com.example.narasimha.androidtestapp.model.VehicleResponse
import retrofit2.Response
import retrofit2.http.GET

interface VehicleApiService {
    @GET("vehicles/getallmanufacturers?format=json")
    suspend fun getAllManufacturers(): Response<VehicleResponse>
}
