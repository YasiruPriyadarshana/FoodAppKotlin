package com.example.foodapp.di

import com.example.foodapp.data.remote.BaseRemoteService
import com.example.foodapp.data.remote.FoodRemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteServiceModule {

    @Provides
    @Singleton
    fun provideBaseRemoteService(): BaseRemoteService {
        return BaseRemoteService()
    }

    @Provides
    @Singleton
    fun provideFoodRemoteService(baseRemoteService: BaseRemoteService): FoodRemoteService {
        return FoodRemoteService(baseRemoteService)
    }
}