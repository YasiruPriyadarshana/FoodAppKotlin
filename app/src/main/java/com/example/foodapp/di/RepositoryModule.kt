package com.example.foodapp.di

import android.content.Context
import com.example.foodapp.data.remote.FoodRemoteService
import com.example.foodapp.data.repository.FoodRepository
import com.example.foodapp.data.sqlite.FoodHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFoodRepository(
        foodHelper: FoodHelper,
        remoteService: FoodRemoteService,
        @ApplicationContext context: Context
    ): FoodRepository {
        return FoodRepository(foodHelper, remoteService, context)
    }
}