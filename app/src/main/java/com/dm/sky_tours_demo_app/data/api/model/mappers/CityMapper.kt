package com.dm.sky_tours_demo_app.data.api.model.mappers

import com.dm.sky_tours_demo_app.data.api.model.ApiCity
import com.dm.sky_tours_demo_app.domain.models.City
import javax.inject.Inject

class CityMapper @Inject constructor() : Mapper<ApiCity, City> {

    override fun mapToDomain(entity: ApiCity): City {
        return City(
            name = entity.name,
            geo = entity.geo
        )
    }
}
