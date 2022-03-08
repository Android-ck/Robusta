package com.zerir.calendarview.adapterData

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zerir.calendarview.R

class DaysAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val days = ArrayList<DayItem>()
    private var dayListener: DayListener? = null
    private var defaultColor: ColorStateList? = null

    fun addListeners(dayListener: DayListener) {
        this.dayListener = dayListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<DayItem>) {
        days.clear()
        days.addAll(list)
        notifyDataSetChanged()
    }

    fun onChange(dayItem: DayItem) {
        days.forEachIndexed { index, item ->
            if (item.id == dayItem.id) {
                item.isSelected = true
                notifyItemChanged(index)
            } else {
                if (item.isSelected) {
                    item.isSelected = false
                    notifyItemChanged(index)
                }
            }
        }
    }

    fun changeColor(color: ColorStateList?) {
        this.defaultColor = color
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DayViewHolder.from(parent, R.layout.row_day_item)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DayViewHolder -> {
                holder.bind(days[position], dayListener, defaultColor)
            }
        }
    }

    override fun getItemCount(): Int = days.size

}