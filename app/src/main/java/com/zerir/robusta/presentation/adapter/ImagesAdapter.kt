package com.zerir.robusta.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zerir.calendarview.CalendarView
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowImageItemBinding
import com.zerir.robusta.presentation.adapter.data.DataItem
import com.zerir.robusta.presentation.utils.ViewHolderBind
import com.zerir.robusta.presentation.utils.ViewHolderDefault
import com.zerir.robusta.presentation.utils.generateBindVH
import com.zerir.robusta.presentation.utils.generateDefaultVH

class ImageAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(ImageDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.row_calendar_item -> generateDefaultVH(
                parent,
                R.layout.row_calendar_item,
            ) { view, position ->
                view.findViewById<CalendarView>(R.id.calendar).apply {
                    val item = (currentList[position] as DataItem.CalendarItem)
                    item.observeDayItem(selectedDay)
                }
            }
            R.layout.row_image_item -> generateBindVH<RowImageItemBinding>(
                parent,
                R.layout.row_image_item,
            ) { binding, position ->
                val item = (currentList[position] as DataItem.ImageItem)
                binding.image = item.image
                binding.root.setOnClickListener { item.onImageClicked(item.image) }
            }
            R.layout.row_horizontal_rv_item -> generateDefaultVH(
                parent,
                R.layout.row_horizontal_rv_item
            ) { view, position ->
                val adapter = HorizontalAdapter()
                val item = (currentList[position] as DataItem.HorizontalImagesItem)
                view.findViewById<RecyclerView>(R.id.horizontal_rv).apply {
                    adapter.submitList(item.images)
                    this.adapter = adapter
                    this.setHasFixedSize(true)
                }
            }
            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderDefault -> holder.bind(position)
            is ViewHolderBind<*> -> holder.bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.CalendarItem -> R.layout.row_calendar_item
            is DataItem.ImageItem -> R.layout.row_image_item
            is DataItem.HorizontalImagesItem -> R.layout.row_horizontal_rv_item
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