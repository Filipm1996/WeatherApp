package com.example.weathertask.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weathertask.MainCoroutineRule
import com.example.weathertask.common.Resource
import com.example.weathertask.data.storage.WeatherDbRepository
import com.example.weathertask.domain.model.City
import com.example.weathertask.domain.model.Weather
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherForCitiesUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var getWeatherForCitiesUseCase: GetWeatherForCitiesUseCase

    @MockK
    private lateinit var getWeatherForCityUseCase: GetWeatherForCityUseCase

    @MockK
    private lateinit var weatherDbRepository: WeatherDbRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getWeatherForCitiesUseCase =
            GetWeatherForCitiesUseCase(getWeatherForCityUseCase, weatherDbRepository)
    }

    @Test
    fun `run return Success`() = runTest {
        /* Given */
        val city = City(2.0, 2.0, "name", "GB")
        val weather = mock(Weather::class.java)
        val cityList = listOf(city)
        val successResource = Resource.Success(weather)
        coEvery { weatherDbRepository.getCities() } returns cityList
        coEvery {
            getWeatherForCityUseCase.run(
                GetWeatherForCityUseCase.Input(
                    city.lat,
                    city.lon,
                    city.name
                )
            )
        } returns successResource

        /* When */
        val result = getWeatherForCitiesUseCase.run(Unit)

        /* Then */
        assertEquals(
            Resource.Success(listOf(weather)),
            result
        )
    }

    @Test
    fun `run return Error`() = runTest {
        /* Given */
        val city = City(2.0, 2.0, "name", "GB")
        val cityList = listOf(city)
        val errorResource = Resource.Error<Weather>("error")
        coEvery { weatherDbRepository.getCities() } returns cityList
        coEvery {
            getWeatherForCityUseCase.run(
                GetWeatherForCityUseCase.Input(
                    city.lat,
                    city.lon,
                    city.name
                )
            )
        } returns errorResource

        /* When */
        val result = getWeatherForCitiesUseCase.run(Unit)

        /* Then */
        assertEquals(
            Resource.Error<List<Weather>>("error"),
            result
        )
    }
}