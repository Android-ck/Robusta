package com.zerir.calendarview.adapterData

data class DayItem(
    val id: String,
    val nameInWeek: String,
    val numberInMonth: Int,
    val month: Int,
    var isSelected: Boolean,
    val isCurrentDay: Boolean,
)
