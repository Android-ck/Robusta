package com.zerir.robusta.presentation.adapter.data

import androidx.lifecycle.LiveData
import com.zerir.calendarview.adapterData.DayItem

interface CalendarListener {
    fun observeCalendar(selectedDay: LiveData<DayItem>)
}