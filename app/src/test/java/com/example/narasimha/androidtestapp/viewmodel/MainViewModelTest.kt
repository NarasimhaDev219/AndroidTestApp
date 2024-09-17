package com.example.narasimha.androidtestapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.narasimha.androidtestapp.model.Manufacturer
import com.example.narasimha.androidtestapp.model.VehicleResponse
import com.example.narasimha.androidtestapp.network.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // TestCoroutineDispatcher for managing coroutines
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: VehicleRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)  // Set Main dispatcher to the test dispatcher
        repository = mock()
        viewModel = MainViewModel(repository)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()  // Reset Main dispatcher after the test
    }

@Test
fun `fetchManufacturers updates LiveData on success`() = runTest {
    // Given
    val manufacturerList = listOf(
        Manufacturer(
            Country = "UNITED STATES (USA)",
            Mfr_CommonName = "Tesla",
            Mfr_ID = 955,
            Mfr_Name = "TESLA, INC.",
            VehicleTypes = emptyList()
        )
    )

    val vehicleResponse = VehicleResponse(
        Count = 1,
        Message = "Response returned successfully",
        Results = manufacturerList
    )

    // Mock repository response
    whenever(repository.getManufacturers()).thenReturn(Result.success(vehicleResponse))

    // Mock observer
    val manufacturersObserver: Observer<List<Manufacturer>> = mock()

    // Attach observer to LiveData
    viewModel.manufacturers.observeForever(manufacturersObserver)

    // When
    viewModel.fetchManufacturers()

    // Advance the coroutine until idle
    advanceUntilIdle()

    // Then
    verify(manufacturersObserver).onChanged(manufacturerList)
}


    @Test
    fun `fetchManufacturers updates error LiveData on failure`() = runTest {
        // Given
        val errorMessage = "Failed to fetch manufacturers"
        whenever(repository.getManufacturers()).thenReturn(Result.failure(Exception(errorMessage)))

        // Mock observers
        val manufacturersObserver: Observer<List<Manufacturer>> = mock()
        val errorObserver: Observer<String?> = mock()

        viewModel.manufacturers.observeForever(manufacturersObserver)
        viewModel.error.observeForever(errorObserver)

        // When
        viewModel.fetchManufacturers()

        // Advance until all coroutines have completed
        advanceUntilIdle()

        // Then
        verify(errorObserver).onChanged(errorMessage)
    }
}