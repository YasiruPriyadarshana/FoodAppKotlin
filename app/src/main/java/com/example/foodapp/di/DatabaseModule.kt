package com.example.foodapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.foodapp.data.sqlite.BaseDatabaseHelper
import com.example.foodapp.data.sqlite.FoodHelper
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BaseDatabaseHelper {
        return BaseDatabaseHelper.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideFoodHelper(databaseHelper: BaseDatabaseHelper): FoodHelper {
        return FoodHelper(databaseHelper)
    }
}