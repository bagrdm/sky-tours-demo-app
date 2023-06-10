package com.dm.sky_tours_demo_app.data.api.model.mappers

import com.dm.sky_tours_demo_app.data.api.model.ApiHotel
import com.dm.sky_tours_demo_app.domain.models.Hotel
import com.dm.sky_tours_demo_app.domain.models.Location
import javax.inject.Inject

class HotelMapper @Inject constructor() : Mapper<ApiHotel, Hotel> {

    override fun mapToDomain(entity: ApiHotel): Hotel {
        return Hotel(
            name = entity.name,
            thumb = entity.thumb,
            location = Location(
                entity.location.address
            )
        )
    }
}
