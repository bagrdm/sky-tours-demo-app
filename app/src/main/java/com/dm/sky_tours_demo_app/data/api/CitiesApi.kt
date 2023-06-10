package com.dm.sky_tours_demo_app.data.api

import com.dm.sky_tours_demo_app.data.api.ApiConst.CITIES_ENDPOINT
import com.dm.sky_tours_demo_app.data.api.ApiParams.INPUT
import com.dm.sky_tours_demo_app.data.api.ApiParams.METHOD
import com.dm.sky_tours_demo_app.data.api.model.ResponseCities
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApi {

    @GET(CITIES_ENDPOINT)
    suspend fun getCities(
        @Query(METHOD) method: String,
        @Query(INPUT) input: String
    ): ResponseCities
}
