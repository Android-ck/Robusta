package com.zerir.robusta.presentation.adapter.data

import com.zerir.robusta.domain.model.Image

interface ImageListener {
    fun onImageClicked(image: Image)
}