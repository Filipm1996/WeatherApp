package com.example.weatherapp.features.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.weatherapp.MainCoroutineRule
import com.example.weatherapp.R
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.DeleteCityUseCase
import com.example.weatherapp.domain.GetCitiesUseCase
import com.example.weatherapp.domain.GetWeatherForCitiesUseCase
import com.example.weatherapp.domain.GetWeatherForCityUseCase
import com.example.weatherapp.domain.InsertCityUseCase
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.features.citylist.viewmodel.CityListViewModel
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
class CityListViewModelTest {

    private lateinit var viewModel: CityListViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @MockK
    private lateinit var getWeatherForCityUseCase: GetWeatherForCityUseCase

    @MockK
    private lateinit var getWeatherForCitiesUseCase: GetWeatherForCitiesUseCase

    @MockK
    private lateinit var deleteCityUseCase: DeleteCityUseCase

    @MockK
    private lateinit var insertCityUseCase: InsertCityUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = CityListViewModel(
            getCitiesUseCase,
            getWeatherForCityUseCase,
            getWeatherForCitiesUseCase,
            deleteCityUseCase,
            insertCityUseCase
        )
    }

    @Test
    fun `get success response from getCities send cities to channel`() = runTest {
        /* Given */
        val successResource = Resource.Success<List<City>>(listOf(city))
        coEvery { getCitiesUseCase.run(any()) } returns successResource

        /* When */
        viewModel.getCities("cityName")

        /*Then*/
        viewModel.actions.test {
            assertEquals(
                CityListViewModel.Action.ShowCityPickerDialog(listOf(city)),
                awaitItem()
            )
        }
    }


    @Test
    fun `get error response from getCities send error to channel`() = runTest {
        /* Given */
        val errorResource = Resource.Error<List<City>>("Error")
        coEvery { getCitiesUseCase.run(any()) } returns errorResource

        /* When */
        viewModel.getCities("cityName")

        /*Then*/
        viewModel.actions.test {
            assertEquals(
                CityListViewModel.Action.ShowError("Error"),
                awaitItem()
            )
        }
    }

    @Test
    fun `get success response from getWeatherForCities send list to channel`() = runTest {
        /* Given */
        val weatherList = listOf(weather)
        val successResource = Resource.Success(weatherList)
        coEvery { getWeatherForCitiesUseCase.run(Unit) } returns (successResource)

        /* When */
        viewModel.getCitiesWithWeather()

        /*Then*/
        viewModel.actions.test {
            assertEquals(CityListViewModel.Action.ShowLoading, awaitItem())
            assertEquals(CityListViewModel.Action.ShowCitiesWithWeather(weatherList), awaitItem())
            assertEquals(CityListViewModel.Action.HideLoading, awaitItem())
        }
    }

    //
    @Test
    fun `get error response from getWeatherForCities send error to channel`() = runTest {
        /* Given */
        val errorResource = Resource.Error<List<Weather>>("Error")
        coEvery { getWeatherForCitiesUseCase.run(Unit) } returns (errorResource)

        /* When */
        viewModel.getCitiesWithWeather()

        /*Then*/
        viewModel.actions.test {
            assertEquals(CityListViewModel.Action.ShowLoading, awaitItem())
            assertEquals(CityListViewModel.Action.ShowError("Error"), awaitItem())
            assertEquals(CityListViewModel.Action.HideLoading, awaitItem())
        }
    }

    @Test
    fun `insertCity send Success to channel and get weather for city`() = runTest {
        /* Given */
        val city = City(2.0, 2.0, "name", "GB")
        val successResource = Resource.Success<Weather>(weather)
        coEvery { insertCityUseCase.run(any()) } returns true
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
        viewModel.insertCity(city)

        /* Then */
        viewModel.actions.test {
            // val item1 = awaitItem()
            assertEquals(
                CityListViewModel.Action.ShowSuccess(Resource.Success("Success")),
                awaitItem()
            )
            // assertThat(item1).isInstanceOf(CityListViewModel.Action.ShowSuccess(Resource.Success("Success"))::class.java)
            assertEquals(CityListViewModel.Action.ShowWeatherForCity(weather), awaitItem())
        }
    }

    @Test
    fun `insertCity send Error to channel`() = runTest {
        /* Given */
        val city = City(2.0, 2.0, "name", "GB")
        val successResource = Resource.Success<Weather>(weather)
        coEvery { insertCityUseCase.run(any()) } returns false
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
        viewModel.insertCity(city)

        /* Then */
        viewModel.actions.test {
            assertEquals(
                CityListViewModel.Action.ShowErrorRes(R.string.city_already_added_error),
                awaitItem()
            )
        }
    }

    @Test
    fun `insertCity send Success then error from getWeatherForCity to channel`() = runTest {
        /* Given */
        val city = City(2.0, 2.0, "name", "GB")
        val errorResource = Resource.Error<Weather>("Error")
        coEvery { insertCityUseCase.run(any()) } returns true
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
        viewModel.insertCity(city)

        /* Then */
        viewModel.actions.test {
            assertEquals(
                CityListViewModel.Action.ShowSuccess(Resource.Success("Success")),
                awaitItem()
            )
            assertEquals(CityListViewModel.Action.ShowError("Error"), awaitItem())
        }
    }

    companion object {
        private val weather = mock(Weather::class.java)
        private val city = mock(City::class.java)
    }
}