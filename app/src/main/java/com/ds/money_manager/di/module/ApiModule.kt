package com.ds.money_manager.di.module

import com.ds.money_manager.data.repository.api.MoneyManagerApi
import com.ds.money_manager.data.repository.api.interceptors.AuthInterceptor
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandlerImpl
import com.ds.money_manager.data.repository.preferences.AppSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [CommonModule::class])
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    private const val BASE_URL =
        "https://moneymanagerapi-app-202301200100.politetree-d972de88.germanywestcentral.azurecontainerapps.io/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideAuthInterceptor(preferences: AppSharedPreferences) = AuthInterceptor(preferences)

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MoneyManagerApi =
        retrofit.create(MoneyManagerApi::class.java)

    @Provides
    @Singleton
    fun provideMoneyManagerDataHandler(moneyManagerApi: MoneyManagerApi): MoneyManagerDataHandler {
        return MoneyManagerDataHandlerImpl(moneyManagerApi)
    }
}