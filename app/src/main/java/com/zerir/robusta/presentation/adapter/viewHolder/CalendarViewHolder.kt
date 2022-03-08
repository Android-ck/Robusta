package com.zerir.robusta.presentation.adapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.zerir.calendarview.CalendarView
import com.zerir.robusta.R
import com.zerir.robusta.presentation.adapter.data.CalendarListener

class CalendarViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun from(parent: ViewGroup) : CalendarViewHolder {
            val context = parent.context
            val layoutInflater = LayoutInflater.from(context)
            val view = layoutInflater.inflate(R.layout.row_calendar_item, parent, false)
            return CalendarViewHolder(view)
        }
    }

    fun bind(calendarListener: CalendarListener?) {
        view.findViewById<CalendarView>(R.id.calendar).apply {
            calendarListener?.observeCalendar(selectedDay)
        }
    }
}