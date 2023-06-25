package com.dm.sky_tours_demo_app.ui.fragments.hotels_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.sky_tours_demo_app.domain.models.City
import com.dm.sky_tours_demo_app.domain.models.Hotel
import com.dm.sky_tours_demo_app.domain.usecases.GetCitiesUseCase
import com.dm.sky_tours_demo_app.domain.usecases.GetHotelsUseCase
import com.dm.sky_tours_demo_app.ui.fragments.utils.DateHolder
import com.dm.sky_tours_demo_app.ui.model.RoomsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelsViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getHotelsUseCase: GetHotelsUseCase
) : ViewModel() {

    private val _date = MutableStateFlow(DateHolder())
    private val _hotels = MutableStateFlow<List<Hotel>>(emptyList())
    private val _currentCity = MutableStateFlow(City())
    private val _cities = MutableStateFlow<List<City>>(emptyList())
    private val _rooms = MutableStateFlow(RoomsData(0,0, 0, emptyList()))

    val date: StateFlow<DateHolder> get() = _date
    val currentCity: StateFlow<City> get() = _currentCity
    val cities: StateFlow<List<City>> get() = _cities
    val rooms: StateFlow<RoomsData> get() = _rooms
    val hotels: StateFlow<List<Hotel>> get() = _hotels

    fun updateRooms(rooms: RoomsData) {
        viewModelScope.launch {
            _rooms.emit(rooms)
        }
    }

    fun updateFromToDate(from: String, to: String) {
        viewModelScope.launch {
            _date.emit(DateHolder(from, to))
        }
    }

    //    TODO(нужен рефакторинг)
    fun fetchCities(text: String) {
        viewModelScope.launch {
            val list = getCitiesUseCase.invoke(text)
            _cities.emit(list)
        }
    }

    fun fetchHotels() {
        val geo = _currentCity.value.geo
        val date = _date.value.getQueryDate()
        val roomsCount = _rooms.value.roomsCount
        val adultCount = _rooms.value.adultCount
        val childrenCount = _rooms.value.childCount
        val childrenAge = if (_rooms.value.childCount == 0) "" else ":${_rooms.value.childrenAge}"
        val a = "sky-tours"
        val method = "search"
        val q = "$geo|$date|$roomsCount|$adultCount:$childrenCount$childrenAge|100|||US|hotel"
        val curr = "USD"
        val lang = "en"

        viewModelScope.launch {
            val list = getHotelsUseCase.invoke(a, method, q, curr, lang)
            _hotels.emit(list)
        }
    }

    fun setCurrentCity(city: City) {
        viewModelScope.launch {
            _currentCity.emit(city)
        }
    }

    fun clearCities() {
        viewModelScope.launch {
            _cities.emit(emptyList())
        }
    }
}
