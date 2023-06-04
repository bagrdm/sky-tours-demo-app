package com.dm.sky_tours_demo_app.data

import com.dm.sky_tours_demo_app.data.api.CitiesApi
import com.dm.sky_tours_demo_app.data.api.model.mappers.CityMapper
import com.dm.sky_tours_demo_app.domain.models.SearchCity
import com.dm.sky_tours_demo_app.domain.repository.HotelsRepository
import javax.inject.Inject

class HotelsRepositoryImpl @Inject constructor(
    private val api: CitiesApi,
    private val mapper: CityMapper
) : HotelsRepository {

    override suspend fun getCities(text: String): List<SearchCity> {
        return api.getCities("geocode", text).cities.map {
            mapper.mapToDomain(it)
        }
    }
}
