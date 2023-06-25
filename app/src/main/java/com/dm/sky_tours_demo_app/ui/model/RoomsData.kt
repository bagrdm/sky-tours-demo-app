package com.dm.sky_tours_demo_app.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomsData(
    val roomsCount: Int,
    val adultCount: Int,
    val childCount: Int,
    val childrenAge: List<Int>
) : Parcelable
