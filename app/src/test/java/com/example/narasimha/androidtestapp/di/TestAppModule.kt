package com.example.narasimha.androidtestapp.di

import dagger.hilt.components.SingletonComponent
import org.mockito.Mockito.mock
import com.example.narasimha.androidtestapp.viewmodel.VehicleViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideVehicleViewModel(): VehicleViewModel {
        // Return a mock ViewModel or use a real implementation if needed
        return mock(VehicleViewModel::class.java)
    }
}
