package com.zerir.calendarview.manager

import androidx.lifecycle.LiveData
import com.zerir.calendarview.adapterData.DayItem
import com.zerir.calendarview.adapterData.DaysAdapter

interface CalendarManager {

    val daysAdapter: DaysAdapter

    val scrollTo: Int

    val monthWithYearName: String

    val selectedDay: LiveData<DayItem>

    fun changeMonth(isForward: Boolean): String
}