package com.zerir.robusta.presentation.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewHolderDefault(
    view: View,
    private val onBind: (View, Int) -> Unit,
) : RecyclerView.ViewHolder(view) {
    fun bind(position: Int) { onBind(itemView, position) }
}

class ViewHolderBind<T: ViewDataBinding>(
    private val binding: T,
    private val onBind: (T, Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bind(position: Int) { onBind(binding, position) }
}

fun generateDefaultVH(
    parent: ViewGroup,
    @LayoutRes layout: Int,
    onBind: (View, Int) -> Unit,
) : ViewHolderDefault {
    val context = parent.context
    val layoutInflater = LayoutInflater.from(context)
    val view = layoutInflater.inflate(layout, parent, false)
    return ViewHolderDefault(view, onBind)
}

fun <T: ViewDataBinding> generateBindVH(
    parent: ViewGroup,
    @LayoutRes layout: Int,
    onBind: (T, Int) -> Unit,
) : ViewHolderBind<T> {
    val context = parent.context
    val layoutInflater = LayoutInflater.from(context)
    val binding = DataBindingUtil.inflate<T>(layoutInflater, layout, parent, false)
    return ViewHolderBind(binding, onBind)
}