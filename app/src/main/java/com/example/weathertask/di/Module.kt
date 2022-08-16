package com.example.weathertask.di

import android.content.Context
import androidx.room.Room
import com.example.weathertask.data.network.WeatherApiRepository
import com.example.weathertask.data.network.WeatherApiRepositoryImpl
import com.example.weathertask.data.network.api.ApiService
import com.example.weathertask.data.network.setup.OkHttpClientClass
import com.example.weathertask.data.network.setup.RetrofitInstance
import com.example.weathertask.data.network.setup.ServiceInterceptor
import com.example.weathertask.data.storage.CityDb
import com.example.weathertask.data.storage.WeatherDbRepository
import com.example.weathertask.data.storage.WeatherDbRepositoryImpl
import com.example.weathertask.data.storage.dao.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        serviceInterceptor: ServiceInterceptor
    ): OkHttpClientClass = OkHttpClientClass(serviceInterceptor)

    @Singleton
    @Provides
    fun provideServiceInterceptor() = ServiceInterceptor()

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClientClass
    ): RetrofitInstance = RetrofitInstance(okHttpClient)

    @Singleton
    @Provides
    fun provideApi(
        retrofitInstance: RetrofitInstance
    ): ApiService = retrofitInstance.getApi()

    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService,
    ) = WeatherApiRepositoryImpl(apiService) as WeatherApiRepository

    @Singleton
    @Provides
    fun provideCityDb(
        @ApplicationContext context: Context
    ) = Room
        .databaseBuilder(context, CityDb::class.java, "cityDb")
        .build()

    @Singleton
    @Provides
    fun provideCityDao(
        cityDb: CityDb
    ) = cityDb.cityDao()

    @Singleton
    @Provides
    fun provideWeatherDbRepository(
        cityDao: CityDao
    ) = WeatherDbRepositoryImpl(cityDao) as WeatherDbRepository
}