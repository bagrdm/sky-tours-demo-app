package com.dm.sky_tours_demo_app.data.api.model.mappers

interface Mapper<E, D> {
    fun mapToDomain(entity: E): D
}
