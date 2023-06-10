package com.dm.sky_tours_demo_app.data.api.repositories

import com.dm.sky_tours_demo_app.data.api.CitiesApi
import com.dm.sky_tours_demo_app.data.api.model.mappers.CityMapper
import com.dm.sky_tours_demo_app.domain.models.City
import com.dm.sky_tours_demo_app.domain.repository.CitiesRepository
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val api: CitiesApi,
    private val mapper: CityMapper
) : CitiesRepository {

    override suspend fun getCities(input: String): List<City> {
        return api.getCities(method = "geocode", input = input).cities.map {
            mapper.mapToDomain(it)
        }
    }
}
