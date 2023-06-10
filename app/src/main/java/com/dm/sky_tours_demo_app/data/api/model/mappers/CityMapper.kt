package com.dm.sky_tours_demo_app.data.api.model.mappers

import com.dm.sky_tours_demo_app.data.api.model.City
import com.dm.sky_tours_demo_app.domain.models.SearchCity
import javax.inject.Inject

class CityMapper @Inject constructor() : Mapper<City, SearchCity> {

    override fun mapToDomain(entity: City): SearchCity {
        return SearchCity(
            name = entity.name,
            geo = entity.geo
        )
    }
}
