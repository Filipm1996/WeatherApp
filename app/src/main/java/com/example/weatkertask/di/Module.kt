package com.example.weatkertask.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatkertask.data.Retrofit.OkHttpClientClass
import com.example.weatkertask.data.Retrofit.RetrofitInstance
import com.example.weatkertask.data.Retrofit.ServiceInterceptor
import com.example.weatkertask.data.Retrofit.api.ApiService
import com.example.weatkertask.data.db.CityDao
import com.example.weatkertask.data.db.CityDb
import com.example.weatkertask.data.repositories.Repository
import com.example.weatkertask.data.repositories.RepositoryDefault
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
    ) : OkHttpClientClass = OkHttpClientClass(serviceInterceptor)

    @Singleton
    @Provides
    fun provideServiceInterceptor()= ServiceInterceptor()

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClientClass
    ) : RetrofitInstance = RetrofitInstance(okHttpClient)

    @Singleton
    @Provides
    fun provideApi (
        retrofitInstance: RetrofitInstance
    ) : ApiService = retrofitInstance.getApi()

    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService,
        cityDao: CityDao
    ) = Repository(apiService,cityDao) as RepositoryDefault

    @Singleton
    @Provides
    fun provideCityDb (
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, CityDb::class.java, "cityDb").fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCityDao(
        cityDb : CityDb
    ) = cityDb.cityDao()
}