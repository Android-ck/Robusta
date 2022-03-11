package com.zerir.calendarview.manager

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zerir.calendarview.adapterData.DayItem
import com.zerir.calendarview.adapterData.DayListener
import com.zerir.calendarview.adapterData.DaysAdapter
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class CalendarManagerImpl : CalendarManager {

    private val _daysAdapter = DaysAdapter()

    private var _selectedDay = MutableLiveData<DayItem>()

    private val _calendar = Calendar.getInstance()

    init {
        /** set selectedDay */
        _selectedDay.value = getDayOfMonth()
        /** add adapter listeners */
        addAdaptersListeners()
        /** setup days for calendar */
        _daysAdapter.submitList(getMonthDays())
    }

    override val daysAdapter: DaysAdapter get() = _daysAdapter

    override val scrollTo: Int
        get() = when {
            _selectedDay.value!!.numberInMonth <= 5 -> 0
            _selectedDay.value!!.numberInMonth in 6..27 -> _selectedDay.value!!.numberInMonth + 1
            else -> _daysAdapter.itemCount - 1
        }

    override val monthWithYearName: String
        get() =
            SimpleDateFormat("MMM, yyyy").format(_calendar.time)

    override val selectedDay: LiveData<DayItem>
        get() = _selectedDay

    override fun changeMonth(isForward: Boolean): String {
        if (isForward) {
            _calendar.add(Calendar.MONTH, 1)
            _daysAdapter.submitList(getMonthDays())
        } else {
            _calendar.add(Calendar.MONTH, -1)
            _daysAdapter.submitList(getMonthDays())
        }
        // reset calendar day
        val calendarDay =
            if (_calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
                _calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)
            ) Calendar.getInstance().get(Calendar.DAY_OF_MONTH) else 1
        _calendar.set(Calendar.DAY_OF_MONTH, calendarDay)
        // reset selected day
        _selectedDay.value = getDayOfMonth()
        _daysAdapter.onChange(_selectedDay.value!!)
        // return new month name
        return monthWithYearName
    }

    private fun addAdaptersListeners() {
        _daysAdapter.addListeners(object : DayListener {
            override fun onDayClicked(dayItem: DayItem) {
                setDay(dayItem)
            }
        })
    }

    private fun setDay(dayItem: DayItem) {
        if (dayItem.id == _selectedDay.value?.id) return
        // update selected day
        _selectedDay.value = dayItem
        _daysAdapter.onChange(_selectedDay.value!!)
        // set calendar day
        _calendar.set(Calendar.DAY_OF_MONTH, dayItem.numberInMonth)
    }

    private fun getDayOfMonth(): DayItem {
        val id =
            "${_calendar.get(Calendar.YEAR)} ${_calendar.get(Calendar.MONTH)} ${
                _calendar.get(
                    Calendar.DAY_OF_MONTH
                )
            }"

        val dayNumber = _calendar.get(Calendar.DAY_OF_MONTH)

        return DayItem(
            id = id,
            nameInWeek = getDayNameInWeek(_calendar),
            numberInMonth = dayNumber,
            month = _calendar.get(Calendar.MONTH),
            isSelected = true,
            isCurrentDay = checkIfDayIsCurrentDay(dayNumber),
        )
    }

    private fun getDayNameInWeek(calendar: Calendar): String =
        SimpleDateFormat("EEE").format(calendar.time)

    private fun getMonthDays(): List<DayItem> {
        val maxDays = _calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val newCal = Calendar.getInstance()
        newCal.set(Calendar.YEAR, _calendar.get(Calendar.YEAR))
        newCal.set(Calendar.MONTH, _calendar.get(Calendar.MONTH))

        return (1..maxDays).map { dayNumber ->
            val id = "${_calendar.get(Calendar.YEAR)} ${_calendar.get(Calendar.MONTH)} $dayNumber"

            newCal.set(Calendar.DAY_OF_MONTH, dayNumber)

            DayItem(
                id = id,
                nameInWeek = getDayNameInWeek(newCal),
                numberInMonth = dayNumber,
                month = newCal.get(Calendar.MONTH),
                isSelected = _selectedDay.value?.id == id,
                isCurrentDay = checkIfDayIsCurrentDay(dayNumber)
            )
        }
    }

    private fun checkIfDayIsCurrentDay(dayNumberInMonth: Int): Boolean =
        if (isSameMonthAndYear()) {
            dayNumberInMonth == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        } else false

    private fun isSameMonthAndYear(): Boolean =
        _calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
                _calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)

}