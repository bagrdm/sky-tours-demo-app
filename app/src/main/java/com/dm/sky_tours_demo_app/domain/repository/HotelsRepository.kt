package com.dm.sky_tours_demo_app.domain.repository

import com.dm.sky_tours_demo_app.domain.models.SearchCity

interface HotelsRepository {

    suspend fun getCities(text: String): List<SearchCity>
}
