package com.dm.sky_tours_demo_app.domain.usecases

import com.dm.sky_tours_demo_app.domain.models.SearchCity
import com.dm.sky_tours_demo_app.domain.repository.HotelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: HotelsRepository
) {
    suspend operator fun invoke(text: String): List<SearchCity> {
        return withContext(Dispatchers.IO) {
            repository.getCities(text)
        }
    }
}
