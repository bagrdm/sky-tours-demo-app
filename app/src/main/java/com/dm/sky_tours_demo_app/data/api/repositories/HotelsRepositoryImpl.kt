package com.dm.sky_tours_demo_app.data.api.repositories

import com.dm.sky_tours_demo_app.data.api.HotelsApi
import com.dm.sky_tours_demo_app.data.api.model.mappers.HotelMapper
import com.dm.sky_tours_demo_app.domain.models.Hotel
import com.dm.sky_tours_demo_app.domain.repository.HotelsRepository
import javax.inject.Inject

class HotelsRepositoryImpl @Inject constructor(
    private val api: HotelsApi,
    private val mapper: HotelMapper
) : HotelsRepository {

    override suspend fun getHotels(
        a: String, method: String, q: String, curr: String, lang: String
    ): List<Hotel> {
        return api.getHotels(a, method, q, curr, lang).response.map {
            mapper.mapToDomain(it)
        }
    }
}
