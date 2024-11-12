package com.ds.money_manager.di.module

import android.content.Context
import com.ds.money_manager.data.repository.preferences.AppSharedPreferences
import com.ds.money_manager.data.repository.preferences.AppSharedPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): AppSharedPreferences {
        return AppSharedPreferencesImpl(context)
    }
}