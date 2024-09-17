package com.example.narasimha.androidtestapp.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.narasimha.androidtestapp.model.Manufacturer
import com.example.narasimha.androidtestapp.model.VehicleType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.times

class ManufacturerDetailsViewModelTest {

    // Set up the rule to allow LiveData to work synchronously in unit tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ManufacturerDetailsViewModel

    // Mock the observer for LiveData
    @Mock
    private lateinit var manufacturerObserver: Observer<Manufacturer>

    @Before
    fun setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this)

        // Initialize ViewModel
        viewModel = ManufacturerDetailsViewModel()

        // Attach the observer to LiveData
        viewModel.vehiclesList.observeForever(manufacturerObserver)
    }

    @Test
    fun `setVehiclesList should update LiveData when called`() {
        // Given a Manufacturer object
        val manufacturer = Manufacturer(
            Country = "USA",
            Mfr_CommonName = "General Motors",
            Mfr_ID = 12345,
            Mfr_Name = "GM",
            VehicleTypes = listOf(VehicleType(false,"Passenger car"))
        )

        // When we call setVehiclesList
        viewModel.setVehiclesList(manufacturer)

        // Then the LiveData should update with the given manufacturer
        verify(manufacturerObserver, times(1)).onChanged(manufacturer)
    }

    @Test
    fun `vehiclesList should contain the correct Manufacturer after setVehiclesList`() {
        // Given a Manufacturer object
        val manufacturer = Manufacturer(
            Country = "Germany",
            Mfr_CommonName = "BMW",
            Mfr_ID = 6789,
            Mfr_Name = "BMW AG",
            VehicleTypes = listOf(VehicleType(false,"Passenger car"))
        )

        // When we call setVehiclesList
        viewModel.setVehiclesList(manufacturer)

        // Then the LiveData should contain the correct manufacturer object
        assert(viewModel.vehiclesList.value == manufacturer)
    }
}
