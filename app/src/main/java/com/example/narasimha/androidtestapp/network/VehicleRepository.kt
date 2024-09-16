package com.example.narasimha.androidtestapp.network

import com.example.narasimha.androidtestapp.model.VehicleResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepository @Inject constructor(private val apiService: VehicleApiService) {

    suspend fun getManufacturers(): Result<VehicleResponse> {
        return try {
            val response = apiService.getAllManufacturers()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch manufacturers"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
