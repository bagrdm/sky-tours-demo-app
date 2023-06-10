package com.dm.sky_tours_demo_app.di

import com.dm.sky_tours_demo_app.data.api.ApiConst.BASE_URL
import com.dm.sky_tours_demo_app.data.api.repositories.CitiesRepositoryImpl
import com.dm.sky_tours_demo_app.data.api.ApiConst.CITIES_URL
import com.dm.sky_tours_demo_app.data.api.CitiesApi
import com.dm.sky_tours_demo_app.data.api.HotelsApi
import com.dm.sky_tours_demo_app.data.api.model.mappers.CityMapper
import com.dm.sky_tours_demo_app.data.api.model.mappers.HotelMapper
import com.dm.sky_tours_demo_app.data.api.repositories.HotelsRepositoryImpl
import com.dm.sky_tours_demo_app.domain.repository.CitiesRepository
import com.dm.sky_tours_demo_app.domain.repository.HotelsRepository
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun okHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideCitiesApi(okHttpClient: OkHttpClient, moshi: Moshi): CitiesApi {
        return Retrofit.Builder()
            .baseUrl(CITIES_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CitiesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideHotelsApi(okHttpClient: OkHttpClient, moshi: Moshi): HotelsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(HotelsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCitiesRepository(
        citiesApi: CitiesApi,
        cityMapper: CityMapper
    ): CitiesRepository {
        return CitiesRepositoryImpl(citiesApi, cityMapper)
    }

    @Singleton
    @Provides
    fun providesHotelsRepository(
        hotelsApi: HotelsApi,
        hotelsMapper: HotelMapper
    ): HotelsRepository {
        return HotelsRepositoryImpl(hotelsApi, hotelsMapper)
    }
}
