package com.example.narasimha.androidtestapp.repository

import com.example.narasimha.androidtestapp.model.Manufacturer
import com.example.narasimha.androidtestapp.model.VehicleResponse
import com.example.narasimha.androidtestapp.model.VehicleType
import com.example.narasimha.androidtestapp.network.VehicleApiService
import com.example.narasimha.androidtestapp.network.VehicleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class VehicleRepositoryTest {

    private lateinit var apiService: VehicleApiService
    private lateinit var repository: VehicleRepository

    @Before
    fun setUp() {
        // Initialize the mock API service and repository
        apiService = mock(VehicleApiService::class.java)
        repository = VehicleRepository(apiService)
    }

    @Test
    fun `getManufacturers returns success when response is successful`() = runTest {
        // Prepare test data
        val manufacturers = listOf(
            Manufacturer(
                "UNITED STATES (USA)",
                "Tesla",
                955,
                "TESLA, INC.",
                listOf(
                    VehicleType(true,  "Passenger Car"),
                    VehicleType(false, "Truck"),
                    VehicleType(false,"Multipurpose Passenger Vehicle (MPV)")
                )
            ),
            Manufacturer(
                "UNITED KINGDOM (UK)",
                "Aston Martin",
                956,
                "ASTON MARTIN LAGONDA LIMITED",
                listOf(
                    VehicleType(false, "Passenger Car"),
                    VehicleType(false, "Multipurpose Passenger Vehicle (MPV)")
                )
            )
        )
        val vehicleResponse = VehicleResponse(
           100,
          "Response returned successfully",
           manufacturers
        )
        val response = Response.success(vehicleResponse)

        // Mock API service
        `when`(apiService.getAllManufacturers()).thenReturn(response)

        // Call the method and verify the result
        val result = repository.getManufacturers()
        assert(result.isSuccess)
        assert(result.getOrNull() == vehicleResponse)
    }

    @Test
    fun `getManufacturers returns failure when response is not successful`() = runTest {
        // Prepare test data
        val response = Response.error<VehicleResponse>(400, "".toResponseBody(null))

        // Mock API service
        `when`(apiService.getAllManufacturers()).thenReturn(response)

        // Call the method and verify the result
        val result = repository.getManufacturers()
        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == "Failed to fetch manufacturers")
    }

    @Test
    fun `getManufacturers returns failure when an exception is thrown`() = runTest {
        // Mock API service to throw an exception
        `when`(apiService.getAllManufacturers()).thenThrow(RuntimeException("Network error"))

        // Call the method and verify the result
        val result = repository.getManufacturers()
        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == "Network error")
    }
}
