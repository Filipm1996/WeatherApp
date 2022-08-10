package com.example.weathertask.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weathertask.data.repositories.RepositoryDefault
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

class ViewModelUnitTests {
    private lateinit var viewModel: WeatherViewModel
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: RepositoryDefault

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = WeatherViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }


}