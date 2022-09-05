package com.example.weatherapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.MainCoroutineRule
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.network.WeatherApiRepository
import com.example.weatherapp.domain.model.City
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
class GetCitiesUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var weatherApiRepository: WeatherApiRepository

    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getCitiesUseCase = GetCitiesUseCase(weatherApiRepository)
    }

    @Test
    fun `run return success response from getCities`() = runTest {
        /* Given */
        val city = mock(City::class.java)
        val response = Resource.Success(listOf(city))
        coEvery { weatherApiRepository.getCities(any()) } returns response

        /* When */
        val result = getCitiesUseCase.run("name")

        /* Then */
        assertEquals(response, result)
    }

    @Test
    fun `run return error response from getCities`() = runTest {
        /* Given */
        val response = Resource.Error<List<City>>("Error")
        coEvery { weatherApiRepository.getCities(any()) } returns response

        /* When */
        val result = getCitiesUseCase.run("name")

        /* Then */
        assertEquals(response, result)
    }
}