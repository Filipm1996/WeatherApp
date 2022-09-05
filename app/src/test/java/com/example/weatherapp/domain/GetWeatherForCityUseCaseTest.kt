package com.example.weatherapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.MainCoroutineRule
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.network.WeatherApiRepository
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.model.Weather
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
class GetWeatherForCityUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var weatherApiRepository: WeatherApiRepository

    private lateinit var getWeatherForCityUseCase: GetWeatherForCityUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getWeatherForCityUseCase = GetWeatherForCityUseCase(weatherApiRepository)
    }

    @Test
    fun `run return succes reponse`() = runTest {
        /* Given */
        val city = City(2.0, 2.0, "name", "GB")
        val weather = mock(Weather::class.java)
        val successResponse = Resource.Success(weather)
        coEvery {
            weatherApiRepository.getWeatherForCity(
                city.lat,
                city.lon,
                city.name
            )
        } returns successResponse

        /* When */
        val result = getWeatherForCityUseCase.run(
            GetWeatherForCityUseCase.Input(
                city.lat,
                city.lon,
                city.name
            )
        )

        /* Then */
        assertEquals(successResponse, result)
    }

    @Test
    fun `run return error response`() = runTest {
        /* Given */
        val city = City(2.0, 2.0, "name", "GB")
        val errorResponse = Resource.Error<Weather>("error")
        coEvery {
            weatherApiRepository.getWeatherForCity(
                city.lat,
                city.lon,
                city.name
            )
        } returns errorResponse

        /* When */
        val result = getWeatherForCityUseCase.run(
            GetWeatherForCityUseCase.Input(
                city.lat,
                city.lon,
                city.name
            )
        )

        /* Then */
        assertEquals(errorResponse, result)
    }
}