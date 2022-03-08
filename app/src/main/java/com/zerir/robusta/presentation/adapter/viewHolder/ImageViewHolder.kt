package com.zerir.robusta.presentation.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zerir.robusta.databinding.RowImageItemBinding
import com.zerir.robusta.domain.model.Image
import com.zerir.robusta.presentation.adapter.data.ImageListener

class ImageViewHolder(private val binding: RowImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup) : ImageViewHolder {
            val context = parent.context
            val layoutInflater = LayoutInflater.from(context)
            val binding = RowImageItemBinding.inflate(layoutInflater, parent, false)
            return ImageViewHolder(binding)
        }
    }

    fun bind(image: Image, imageListener: ImageListener?) {
        binding.image = image
        binding.onClick = imageListener
    }
}