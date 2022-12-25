package com.ds.money_manager.di.module

import com.ds.money_manager.data.repository.api.MoneyManagerApi
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    private const val BASE_URL =
        "https://moneymanagerapi-app-202211282312.wittybeach-86b1aed9.germanywestcentral.azurecontainerapps.io/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
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