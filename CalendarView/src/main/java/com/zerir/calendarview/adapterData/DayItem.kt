package com.zerir.calendarview.adapterData

data class DayItem(
    val id: String,
    val nameInWeek: String,
    val numberInMonth: Int,
    var isSelected: Boolean,
    val isCurrentDay: Boolean,
)
