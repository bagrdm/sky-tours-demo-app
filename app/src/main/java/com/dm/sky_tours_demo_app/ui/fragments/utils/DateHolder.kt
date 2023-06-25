package com.dm.sky_tours_demo_app.ui.fragments.utils

data class DateHolder(
    val dateFrom: String = "",
    val dateTo: String = ""
) {
    fun getQueryDate(): String {

        val from = dateFormat(dateFrom)
        val to = dateFormat(dateTo)

        return "$from|$to"
    }

    private fun dateFormat(date: String): String {
        val (day, month, year) = date.split("-")

        return "$year$month$day"
    }
}
