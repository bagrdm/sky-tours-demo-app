package com.dm.sky_tours_demo_app.domain.repository

import com.dm.sky_tours_demo_app.domain.models.Hotel

interface HotelsRepository {

    suspend fun getHotels(
        a: String, method: String, q: String, curr: String, lang: String
    ): List<Hotel>
}
