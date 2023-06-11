package com.dm.sky_tours_demo_app.ui.fragments.utils

import java.text.SimpleDateFormat
import java.util.Locale

data class DateHolder(
    val dateFrom: String = "",
    val dateTo: String = ""
) {
    fun getQueryDate(): String {
        val formatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

        val from = formatter.format(dateFrom)
        val to = formatter.format(dateTo)

        return "$from|$to"
    }
}
