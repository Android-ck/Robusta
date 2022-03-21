package com.zerir.robusta.presentation.controller.model.image

import com.airbnb.epoxy.EpoxyModelClass
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowImagesEmptyItemBinding
import com.zerir.robusta.presentation.controller.ViewBindingKotlinModel

@EpoxyModelClass
abstract class ImagesEmptyModel : ViewBindingKotlinModel<RowImagesEmptyItemBinding>(R.layout.row_images_empty_item) {

    override fun RowImagesEmptyItemBinding.bind() {

    }

}