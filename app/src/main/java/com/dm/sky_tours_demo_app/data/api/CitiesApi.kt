package com.dm.sky_tours_demo_app.data.api

import com.dm.sky_tours_demo_app.data.api.model.ResponseCities
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApi {
    @GET("api.php")
    suspend fun getCities(
        @Query("method") method: String,
        @Query("input") input: String
    ): ResponseCities
}
