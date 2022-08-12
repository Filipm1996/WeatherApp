package com.example.weathertask.features.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weathertask.arch.usecase.UseCaseDefault
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
    lateinit var useCase: UseCaseDefault

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = WeatherViewModel(useCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }


}