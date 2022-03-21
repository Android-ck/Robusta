package com.zerir.robusta.presentation.controller.model.image

import com.airbnb.epoxy.EpoxyModelClass
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowImagesLoadingItemBinding
import com.zerir.robusta.presentation.controller.ViewBindingKotlinModel

@EpoxyModelClass
abstract class ImagesLoadingModel : ViewBindingKotlinModel<RowImagesLoadingItemBinding>(R.layout.row_images_loading_item) {

    override fun RowImagesLoadingItemBinding.bind() {

    }

}