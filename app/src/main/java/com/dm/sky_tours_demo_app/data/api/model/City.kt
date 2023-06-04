package com.dm.sky_tours_demo_app.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    @field:Json(name = "name")
    val name: String
)
