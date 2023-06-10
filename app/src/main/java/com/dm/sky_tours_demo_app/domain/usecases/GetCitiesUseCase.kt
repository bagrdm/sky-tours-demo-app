package com.dm.sky_tours_demo_app.domain.usecases

import com.dm.sky_tours_demo_app.domain.models.City
import com.dm.sky_tours_demo_app.domain.repository.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: CitiesRepository
) {
    suspend operator fun invoke(text: String): List<City> {
        return withContext(Dispatchers.IO) {
            repository.getCities(text)
        }
    }
}
