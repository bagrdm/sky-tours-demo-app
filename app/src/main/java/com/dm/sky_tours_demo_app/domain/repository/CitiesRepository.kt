package com.dm.sky_tours_demo_app.domain.repository

import com.dm.sky_tours_demo_app.domain.models.City

interface CitiesRepository {

    suspend fun getCities(input: String): List<City>
}
