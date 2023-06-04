package com.dm.sky_tours_demo_app.di

import com.dm.sky_tours_demo_app.data.HotelsRepositoryImpl
import com.dm.sky_tours_demo_app.data.api.CitiesApi
import com.dm.sky_tours_demo_app.data.api.model.mappers.CityMapper
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
    fun provideApi(okHttpClient: OkHttpClient, moshi: Moshi): CitiesApi {
        return Retrofit.Builder()
            .baseUrl("https://cp.sky-tours.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CitiesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideHotelsRepository(
        citiesApi: CitiesApi,
        cityMapper: CityMapper
    ): HotelsRepository {
        return HotelsRepositoryImpl(citiesApi, cityMapper)
    }
}
