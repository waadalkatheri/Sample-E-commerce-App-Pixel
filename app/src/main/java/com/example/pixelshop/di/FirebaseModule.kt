package com.example.pixelshop.di

import com.example.pixelshop.data.auth.repository.FirebaseAuthRepository
import com.example.pixelshop.data.auth.repository.FirebaseUserAuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)

class FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: FirebaseUserAuthRepositoryImpl): FirebaseAuthRepository = impl
}