package com.zerir.robusta.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowCircleImageItemBinding
import com.zerir.robusta.presentation.adapter.data.DataItem
import com.zerir.robusta.presentation.utils.ViewHolderBind
import com.zerir.robusta.presentation.utils.generateBindVH

class HorizontalAdapter : ListAdapter<DataItem.ImageItem, RecyclerView.ViewHolder>(HorizontalDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return generateBindVH<RowCircleImageItemBinding>(parent, R.layout.row_circle_image_item) {
            binding, position ->
            val item = currentList[position]
            binding.image = item.image
            binding.root.setOnClickListener { item.onImageClicked(item.image) }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderBind<*> -> holder.bind(position)
        }
    }

}

class HorizontalDiffUtils : DiffUtil.ItemCallback<DataItem.ImageItem>() {

    override fun areItemsTheSame(oldItem: DataItem.ImageItem, newItem: DataItem.ImageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem.ImageItem, newItem: DataItem.ImageItem): Boolean {
        return oldItem == newItem
    }

}