package com.dm.sky_tours_demo_app.ui.fragments.utils

import android.os.Parcel
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.GregorianCalendar

fun createDatePicker(@StyleRes style: Int, @StringRes title: Int) =
    MaterialDatePicker.Builder.dateRangePicker().apply {
        setTheme(style)
        setTitleText(title)
        setCalendarConstraints(limitRange().build())
    }.build()

private fun limitRange(): CalendarConstraints.Builder {

    val startDate = MaterialDatePicker.todayInUtcMilliseconds() - 1
    val endDate = GregorianCalendar.getInstance().apply { set(2122, 1, 1) }.timeInMillis

    return CalendarConstraints.Builder()
        .setStart(startDate)
        .setEnd(endDate)
        .setValidator(object : CalendarConstraints.DateValidator {
            override fun describeContents() = 0

            override fun writeToParcel(dest: Parcel, flags: Int) {}

            override fun isValid(date: Long): Boolean {
                return date in startDate until endDate
            }
        })
}
