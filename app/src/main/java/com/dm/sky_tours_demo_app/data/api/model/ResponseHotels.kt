package com.dm.sky_tours_demo_app.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseHotels(
    @field:Json(name = "response") val response: List<ApiHotel>
)
