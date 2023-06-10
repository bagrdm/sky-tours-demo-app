package com.dm.sky_tours_demo_app.domain.usecases

import com.dm.sky_tours_demo_app.domain.models.Hotel
import com.dm.sky_tours_demo_app.domain.repository.HotelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetHotelsUseCase @Inject constructor(
    private val repository: HotelsRepository
) {
    suspend operator fun invoke(
        a: String, method: String, q: String, curr: String, lang: String
    ): List<Hotel> {
        return withContext(Dispatchers.IO) {
            repository.getHotels(a, method, q, curr, lang)
        }
    }
}
