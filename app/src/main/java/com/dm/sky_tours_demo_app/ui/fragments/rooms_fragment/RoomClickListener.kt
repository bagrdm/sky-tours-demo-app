package com.dm.sky_tours_demo_app.ui.fragments.rooms_fragment

interface RoomClickListener {

    fun closeRoom(position: Int)

    fun addAdult(position: Int)

    fun addChild(position: Int)

    fun removeAdult(position: Int)

    fun removeChild(position: Int)

    fun clickOnContainer(position: Int)
}
