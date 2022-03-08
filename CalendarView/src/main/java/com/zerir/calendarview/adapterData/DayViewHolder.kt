package com.zerir.calendarview.adapterData

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zerir.calendarview.R

class DayViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.dayName_tv)
    private val number = view.findViewById<TextView>(R.id.dayNumber_tv)
    private val dot = view.findViewById<View>(R.id.dayIsCurrent_view)

    companion object {
        fun from(parent: ViewGroup, @LayoutRes layout: Int): DayViewHolder {
            val context = parent.context
            val layoutInflater = LayoutInflater.from(context)
            val view = layoutInflater.inflate(layout, parent, false)
            return DayViewHolder(view)
        }
    }

    fun bind(day: DayItem, dayListener: DayListener?, defaultColor: ColorStateList? = null) {
        view.apply {
            backgroundTintList =
                if (day.isSelected) ContextCompat.getColorStateList(view.context, R.color.blue)
                else defaultColor ?: ContextCompat.getColorStateList(view.context, R.color.white)
            setOnClickListener {
                dayListener?.onDayClicked(day)
            }
        }

        name.apply {
            text = day.nameInWeek
            setTextColor(
                if (day.isSelected) ContextCompat.getColorStateList(view.context, R.color.white)
                else ContextCompat.getColorStateList(view.context, R.color.gray)
            )
        }
        number.apply {
            text = "${day.numberInMonth}"
            setTextColor(
                if (day.isSelected) ContextCompat.getColorStateList(view.context, R.color.white)
                else ContextCompat.getColorStateList(view.context, R.color.black)
            )
        }

        dot.visibility = if (day.isCurrentDay) VISIBLE else GONE
        dot.backgroundTintList =
            if (day.isSelected) ContextCompat.getColorStateList(view.context, R.color.white)
            else ContextCompat.getColorStateList(view.context, R.color.blue)
    }

}