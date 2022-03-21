package com.zerir.robusta.presentation.controller.model.calendar

import androidx.lifecycle.LiveData
import com.airbnb.epoxy.EpoxyModelClass
import com.zerir.calendarview.adapterData.DayItem
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowCalendarItemBinding
import com.zerir.robusta.presentation.controller.ViewBindingKotlinModel

@EpoxyModelClass
abstract class CalendarModel(
    private val calendarListener: (selectedDay: LiveData<DayItem>) -> Unit
) : ViewBindingKotlinModel<RowCalendarItemBinding>(R.layout.row_calendar_item)  {

    override fun RowCalendarItemBinding.bind() {
        calendarListener(calendar.selectedDay)
    }

}