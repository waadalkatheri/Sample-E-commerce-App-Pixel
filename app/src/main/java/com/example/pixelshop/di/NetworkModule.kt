package com.example.pixelshop.di

import android.annotation.SuppressLint
import com.example.pixelshop.data.network.api.ApiService
import com.example.pixelshop.data.network.repository.ProductNetworkRepository
import com.example.pixelshop.data.network.repository.ProductNetworkRepositoryImpl
import com.example.pixelshop.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @SuppressLint("SuspiciousIndentation")
    @Provides
    @Singleton
    fun provideApi(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(apiService: ApiService): ProductNetworkRepository {
        return ProductNetworkRepositoryImpl(apiService = apiService)
    }
}