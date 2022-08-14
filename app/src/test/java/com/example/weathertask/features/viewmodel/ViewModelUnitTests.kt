package com.example.weathertask.features.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.weathertask.MainCoroutineRule
import com.example.weathertask.common.Resource
import com.example.weathertask.domain.model.City
import com.example.weathertask.domain.model.Weather
import com.example.weathertask.features.domain.UseCase
import com.example.weathertask.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelUnitTests {
    private lateinit var viewModel: WeatherViewModel


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: UseCase


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = WeatherViewModel(useCase)
    }


    @Test
    fun `get single response from getWeatherForCity send response to channel`() = runTest {
        val weather = mock(Weather::class.java)
        val response = Resource.Success(weather)
        coEvery { useCase.getWeatherForCity(2.0, 2.0, "name") } returns (response)
        viewModel.getWeatherForCities(listOf(City(2.0, 2.0, "name", "country")))
        viewModel.actions.test {
            val item = awaitItem()
            assertThat(item)
                .isEqualTo(WeatherViewModel.Action.GetWeatherForCities(listOf(weather)))
        }
    }

    @Test
    fun `get response from getWeatherForCity send response to channel`() = runTest {
        val weather = mock(Weather::class.java)
        val weather2 = mock(Weather::class.java)
        val response = Resource.Success(weather)
        val response2 = Resource.Success(weather2)
        val city1 = City(2.0, 2.0, "name", "s")
        val city2 = City(3.0, 3.0, "name2", "s")
        coEvery { useCase.getWeatherForCity(2.0, 2.0, "name") } returns (response)
        coEvery { useCase.getWeatherForCity(3.0, 3.0, "name2") } returns (response2)
        viewModel.getWeatherForCities(listOf(city1, city2))
        viewModel.actions.test {
            val item = awaitItem()
            assertThat(item)
                .isEqualTo(WeatherViewModel.Action.GetWeatherForCities(listOf(weather, weather2)))
        }
    }

    @Test
    fun `get error response from getWeatherForCity send error to ErrorCollector`() = runTest {
        val city = mock(City::class.java)
        val response = Resource.Error<Weather>("Error")
        coEvery { useCase.getWeatherForCity(city.lat, city.lon, city.name) } returns (response)
        viewModel.getWeatherForCities(listOf(city))
        val error = viewModel.getErrorCollector().getOrAwaitValue()
        assertThat(error).isEqualTo("Error")
    }

    @Test
    fun `getCities send city to channel`() = runTest {
        val listOfCities = listOf(City(2.0, 2.0, "s", "s"))
        coEvery { useCase.getCities() } returns (listOfCities)
        viewModel.getCities()
        viewModel.actions.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(WeatherViewModel.Action.GetCitiesFromDb(listOfCities))
        }
    }

    @Test
    fun `getCities send cities to channel`() = runTest {
        val listOfCities = listOf(
            City(2.0, 2.0, "s", "s"),
            City(3.0, 3.0, "d", "d")
        )
        coEvery { useCase.getCities() } returns (listOfCities)
        viewModel.getCities()
        viewModel.actions.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(WeatherViewModel.Action.GetCitiesFromDb(listOfCities))
        }
    }

    @Test
    fun `getCoordinates send list of cities to channel`() = runTest {
        val listOfCities = listOf(
            City(2.0, 2.0, "s", "s"),
            City(3.0, 3.0, "d", "d")
        )
        coEvery { useCase.getCoordinates(any()) } returns Resource.Success(listOfCities)
        viewModel.getCoordinates("Radom")
        viewModel.actions.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(WeatherViewModel.Action.GetCitiesFromApi(listOfCities))
        }
    }

    @Test
    fun `getCoordinates return error and errorCollector contains error`() = runTest {
        val errorResponse = Resource.Error<List<City>>("error")
        coEvery { useCase.getCoordinates("cityName") } returns (errorResponse)
        viewModel.getCoordinates("cityName")
        val error = viewModel.getErrorCollector().getOrAwaitValue()
        assertThat(error).isEqualTo("error")
    }

    @Test
    fun `add existed city to db return error`() = runTest {
        val city = mock(City::class.java)
        coEvery { useCase.ifCityExists(city.name) } returns true
        viewModel.insertCity(city)
        viewModel.actions.test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(WeatherViewModel.Action.InsertCity(Resource.Error("City is already added"))::class.java)
        }
    }

    @Test
    fun `insertCity send Success to channel and get weather for city`() = runTest {
        val weather = mock(Weather::class.java)
        val city = mock(City::class.java)
        val response = Resource.Success<Weather>(weather)
        coEvery { useCase.ifCityExists(any()) } returns false
        coEvery { useCase.getWeatherForCity(any(), any(), any()) } returns response
        viewModel.insertCity(city)
        viewModel.actions.test {
            val item1 = awaitItem()
            assertThat(item1).isInstanceOf(WeatherViewModel.Action.InsertCity(Resource.Success("Success"))::class.java)
            val item2 = awaitItem()
            assertThat(item2).isEqualTo(WeatherViewModel.Action.GetWeatherForCity(weather))
        }
    }

    @Test
    fun `clean error collector set null value for errorCollector`() = runTest {
        val city = mock(City::class.java)
        val errorResponse = Resource.Error<Weather>("error")
        coEvery { useCase.getWeatherForCity(any(), any(), any()) } returns (errorResponse)
        viewModel.getWeatherForCities(listOf(city))
        val error = viewModel.getErrorCollector().getOrAwaitValue()
        assertThat(error).isEqualTo("error")
        viewModel.clearErrorCollector()
        val error2 = viewModel.getErrorCollector().getOrAwaitValue()
        assertThat(error2).isEqualTo(null)
    }

    @Test
    fun `getWeatherForCities set false loading value for loadingCollector`() = runTest {
        val weather = mock(Weather::class.java)
        val response = Resource.Success(weather)
        coEvery { useCase.getWeatherForCity(2.0, 2.0, "name") } returns (response)
        viewModel.getWeatherForCities(listOf(City(2.0, 2.0, "name", "country")))
        val value = viewModel.getLoadingCollector().getOrAwaitValue()
        assertThat(value).isEqualTo(false)
    }
}