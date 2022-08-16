package com.example.weathertask.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weathertask.MainCoroutineRule
import com.example.weathertask.data.storage.WeatherDbRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteCityUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var weatherDbRepository: WeatherDbRepository

    private lateinit var deleteCityUseCase: DeleteCityUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        deleteCityUseCase = DeleteCityUseCase(weatherDbRepository)
    }

    @Test
    fun `run starts deleteCity in repository`() = runTest {
        deleteCityUseCase.run("string")

        coVerify { weatherDbRepository.deleteCity("string") }
    }
}