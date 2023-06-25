package com.dm.sky_tours_demo_app.ui.fragments.rooms_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.sky_tours_demo_app.ui.model.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor() : ViewModel() {

    private val _rooms = MutableStateFlow(listOf(Room()))
    val rooms: StateFlow<List<Room>> get() = _rooms

    //    TODO(need validation)
    fun addRoom() {

        viewModelScope.launch {
            val list = rooms.value.toMutableList()

            list.forEachIndexed { index, room ->
                list[index] = room.copy(isCollapsed = true)
            }

            list.add(Room(id = list.size))

            _rooms.emit(list)
        }
    }

    //    TODO(need validation)
    fun removeRoom(position: Int) {

        viewModelScope.launch {
            val list = _rooms.value.toMutableList()
            list.removeAt(position)

            if (list.isNotEmpty()) {
                list.forEachIndexed { index, room ->
                    list[index] = room.copy(id = index)
                }
            }

            _rooms.emit(list)
        }
    }

    //    TODO(need validation)
    fun addAdult(position: Int) {

        viewModelScope.launch {
            val list = _rooms.value.toMutableList()
            val item = list[position]

            list[position] = item.copy(adultCount = item.adultCount + 1)

            _rooms.emit(list)
        }
    }

    //    TODO(need validation)
    fun addChild(position: Int) {

        viewModelScope.launch {
            val list = _rooms.value.toMutableList()
            val item = list[position]

            list[position] = item.copy(childCount = item.childCount + 1)

            _rooms.emit(list)
        }
    }

    //        TODO(need validation)
    fun removeAdult(position: Int) {

        viewModelScope.launch {
            val list = _rooms.value.toMutableList()
            val item = list[position]

            list[position] = item.copy(adultCount = item.adultCount - 1)

            _rooms.emit(list)
        }
    }

    //        TODO(need validation)
    fun removeChild(position: Int) {

        viewModelScope.launch {
            val list = _rooms.value.toMutableList()
            val item = list[position]

            list[position] = item.copy(childCount = item.childCount - 1)

            _rooms.emit(list)
        }
    }

    //    TODO(need validation)
    fun showDetails(position: Int) {
        val list = _rooms.value.toMutableList()

        list.forEachIndexed { index, room ->
            list[index] = room.copy(isCollapsed = true)
        }

        list[position] = list[position].copy(isCollapsed = false)

        viewModelScope.launch {
            _rooms.emit(list)
        }
    }
}
