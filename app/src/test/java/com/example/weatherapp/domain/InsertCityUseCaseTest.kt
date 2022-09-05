package com.example.weatherapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.MainCoroutineRule
import com.example.weatherapp.data.storage.WeatherDbRepository
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
class InsertCityUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var insertCityUseCase: InsertCityUseCase

    @MockK
    private lateinit var weatherDbRepository: WeatherDbRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        insertCityUseCase = InsertCityUseCase(weatherDbRepository)
    }

    @Test
    fun `retrun false if isCityAlreadyAdded returned true`() = runTest {
        /* Given */
        coEvery { weatherDbRepository.isCityAlreadyAdded(any(), any()) } returns true

        /* When */
        val result = insertCityUseCase.run(city)

        /* Then */
        assertEquals(false, result)
    }

    @Test
    fun `retrun true if isCityAlreadyAdded returned false`() = runTest {
        /* Given */
        coEvery { weatherDbRepository.isCityAlreadyAdded(any(), any()) } returns false

        /* When */
        val result = insertCityUseCase.run(city)

        /* Then */
        assertEquals(true, result)
    }

    companion object {
        private val city = mock(City::class.java)
    }
}