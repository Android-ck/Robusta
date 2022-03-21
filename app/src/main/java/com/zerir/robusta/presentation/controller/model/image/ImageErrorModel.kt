package com.zerir.robusta.presentation.controller.model.image

import com.airbnb.epoxy.EpoxyModelClass
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowImagesErrorItemBinding
import com.zerir.robusta.presentation.controller.ViewBindingKotlinModel

@EpoxyModelClass
abstract class ImageErrorModel(
    private val onRefreshImages: () -> Unit,
) : ViewBindingKotlinModel<RowImagesErrorItemBinding>(R.layout.row_images_error_item) {

    override fun RowImagesErrorItemBinding.bind() {
        errorMsgTv.setOnClickListener { onRefreshImages() }
    }

}