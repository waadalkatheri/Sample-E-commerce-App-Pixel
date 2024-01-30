package com.example.pixelshop.di

import android.content.Context
import androidx.room.Room
import com.example.pixelshop.data.local.AppDatabase
import com.example.pixelshop.data.local.repositories.LocalRepository
import com.example.pixelshop.data.local.repositories.LocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).build()


    @Singleton
    @Provides
    fun provideAppDao(appDatabase: AppDatabase) = appDatabase.appDao()


    @Singleton
    @Provides
    fun provideLocalRepository(impl: LocalRepositoryImpl): LocalRepository = impl
}