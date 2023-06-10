package com.dm.sky_tours_demo_app.data.api

import com.dm.sky_tours_demo_app.data.api.ApiConst.HOTELS_ENDPOINT
import com.dm.sky_tours_demo_app.data.api.ApiParams.A
import com.dm.sky_tours_demo_app.data.api.ApiParams.CURR
import com.dm.sky_tours_demo_app.data.api.ApiParams.LANG
import com.dm.sky_tours_demo_app.data.api.ApiParams.METHOD
import com.dm.sky_tours_demo_app.data.api.ApiParams.Q
import com.dm.sky_tours_demo_app.data.api.model.ResponseHotels
import retrofit2.http.GET
import retrofit2.http.Query

interface HotelsApi {

    @GET(HOTELS_ENDPOINT)
    suspend fun getHotels(
        @Query(A) a: String,
        @Query(METHOD) method: String,
        @Query(Q) q: String,
        @Query(CURR) curr: String,
        @Query(LANG) lang: String
    ): ResponseHotels
}
