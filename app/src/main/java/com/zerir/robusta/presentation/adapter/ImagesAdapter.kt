package com.zerir.robusta.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zerir.robusta.R
import com.zerir.robusta.presentation.adapter.data.CalendarListener
import com.zerir.robusta.presentation.adapter.data.DataItem
import com.zerir.robusta.presentation.adapter.data.ImageListener
import com.zerir.robusta.presentation.adapter.viewHolder.CalendarViewHolder
import com.zerir.robusta.presentation.adapter.viewHolder.ImageViewHolder

class ImageAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(ImageDiffUtils()) {

    private var imageListener: ImageListener? = null
    private var calendarListener: CalendarListener? = null

    fun registerListeners(
        imageListener: ImageListener,
        calendarListener: CalendarListener
    ) {
        this.imageListener = imageListener
        this.calendarListener = calendarListener
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is DataItem.CalendarItem -> R.layout.row_calendar_item
            is DataItem.ImageItem -> R.layout.row_image_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.row_calendar_item -> CalendarViewHolder.from(parent)
            R.layout.row_image_item -> ImageViewHolder.from(parent)
            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CalendarViewHolder -> holder.bind(calendarListener)
            is ImageViewHolder -> holder.bind(
                (getItem(position) as DataItem.ImageItem).image,
                imageListener,
            )
            else -> throw IllegalArgumentException("Invalid ViewHolder")
        }
    }

}

class ImageDiffUtils : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}