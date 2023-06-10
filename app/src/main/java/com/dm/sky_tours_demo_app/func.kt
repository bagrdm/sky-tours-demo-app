

import androidx.fragment.app.Fragment
import com.dm.sky_tours_demo_app.R
import com.dm.sky_tours_demo_app.RangeValidator
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

// TODO(need to improve)
//Calendar
fun limitRange(): CalendarConstraints.Builder {
    val constraintsBuilderRange = CalendarConstraints.Builder()
    val calendarStart: Calendar = GregorianCalendar.getInstance()
    val calendarEnd: Calendar = GregorianCalendar.getInstance()
    calendarStart.set(2022, 1, 1)
    calendarEnd.set(2122, 1, 1)
    val minDate = MaterialDatePicker.todayInUtcMilliseconds() - 1
    val maxDate = calendarEnd.timeInMillis
    constraintsBuilderRange.setStart(minDate)
    constraintsBuilderRange.setEnd(maxDate)
    constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))
    return constraintsBuilderRange
}
