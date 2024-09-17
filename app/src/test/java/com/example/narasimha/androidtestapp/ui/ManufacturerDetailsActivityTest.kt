package com.example.narasimha.androidtestapp.ui

import android.content.Intent
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.example.narasimha.androidtestapp.databinding.ActivityManufacturerBinding
import com.example.narasimha.androidtestapp.model.Manufacturer
import com.example.narasimha.androidtestapp.model.VehicleType
import com.example.narasimha.androidtestapp.viewmodel.ManufacturerDetailsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O], manifest = "src/main/AndroidManifest.xml")
class ManufacturerDetailsActivityTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()  // For LiveData testing

    private lateinit var viewModel: ManufacturerDetailsViewModel
    private lateinit var activityScenario: ActivityScenario<ManufacturerDetailsActivity>
    private lateinit var manufacturerLiveData: MutableLiveData<Manufacturer>

    @Before
    fun setUp() {
        // Mock ViewModel
        viewModel = Mockito.mock(ManufacturerDetailsViewModel::class.java)

        // Prepare MutableLiveData for the test
        manufacturerLiveData = MutableLiveData()
        whenever(viewModel.vehiclesList).thenReturn(manufacturerLiveData)

        // Create intent with mock Manufacturer data
        val manufacturer = Manufacturer(
            Country = "USA",
            Mfr_CommonName = "General Motors",
            Mfr_ID = 12345,
            Mfr_Name = "GM",
            VehicleTypes = listOf(VehicleType(false,"Passenger car"))
        )
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            ManufacturerDetailsActivity::class.java
        ).apply {
            putExtra("MANUFACTURER", manufacturer)
        }

        // Launch the activity
        activityScenario = ActivityScenario.launch<ManufacturerDetailsActivity>(intent)
    }

    @Test
    fun `test if manufacturer details are displayed correctly`() {
        // Given: A manufacturer with data
        val manufacturer = Manufacturer(
            Country = "USA",
            Mfr_CommonName = "General Motors",
            Mfr_ID = 12345,
            Mfr_Name = "GM",
            VehicleTypes = listOf(VehicleType(false,"Passenger car"))
        )

        // When: The LiveData changes
        manufacturerLiveData.postValue(manufacturer)

        // Then: Check if the views are populated with the correct data
        activityScenario.onActivity { activity ->
            val binding =
                ActivityManufacturerBinding.bind(activity.findViewById(android.R.id.content))

            assert(binding.mfrNameID.text == manufacturer.Mfr_Name)
            assert(binding.mfrIdID.text == manufacturer.Mfr_ID.toString())
            assert(binding.mfrCommonNameID.text == manufacturer.Mfr_CommonName)
        }
    }

    @Test
    fun `test RecyclerView shows vehicles when available`() {
        // Given: A manufacturer with a vehicle list
        val manufacturer = Manufacturer(
            Country = "Japan",
            Mfr_CommonName = "Toyota",
            Mfr_ID = 54321,
            Mfr_Name = "Toyota Motor Corporation",
            VehicleTypes = listOf(VehicleType(false,"Passenger car"))
        )

        // When: The LiveData changes
        manufacturerLiveData.postValue(manufacturer)

        // Then: Check if the RecyclerView is updated with vehicle types
        activityScenario.onActivity { activity ->
            val recyclerView = activity.viewBinding.vehiclesRecyclerview

            assert(recyclerView.adapter != null)
            assert(recyclerView.adapter!!.itemCount == manufacturer.VehicleTypes.size)
        }
    }

    @Test
    fun `test no vehicles available message is shown when vehicle list is empty`() {
        // Given: A manufacturer with an empty vehicle list
        val manufacturer = Manufacturer(
            Country = "Germany",
            Mfr_CommonName = "BMW",
            Mfr_ID = 98765,
            Mfr_Name = "BMW AG",
            VehicleTypes = emptyList()
        )

        // When: The LiveData changes
        manufacturerLiveData.postValue(manufacturer)

        // Then: Check if the no vehicles message is shown
        activityScenario.onActivity { activity ->
            val binding =
                ActivityManufacturerBinding.bind(activity.findViewById(android.R.id.content))

            assert(binding.mfrVehicleTypeText.text == "No vehicles available for this Manufacturer")
            //assert(binding.mfrVehicleTypeText.currentTextColor == activity.getColor(com.google.android.material.R.color.material_dynamic_primary10))
        }
    }
}
