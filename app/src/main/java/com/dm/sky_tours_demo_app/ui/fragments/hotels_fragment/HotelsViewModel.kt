package com.dm.sky_tours_demo_app.ui.fragments.hotels_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.sky_tours_demo_app.domain.models.City
import com.dm.sky_tours_demo_app.domain.usecases.GetCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelsViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
) : ViewModel() {

    private val _currentCity = MutableStateFlow(City())
    val currentCity: StateFlow<City> get() = _currentCity

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> get() = _cities

    fun getCities(text: String) {
        viewModelScope.launch {
            val list = getCitiesUseCase.invoke(text)
            _cities.emit(list)
        }
    }

    fun setCurrentCity(city: City) {
        _currentCity.value = city
    }

    fun clearCities() {
        _cities.value = emptyList()
    }
}
