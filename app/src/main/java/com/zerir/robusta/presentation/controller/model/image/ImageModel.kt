package com.zerir.robusta.presentation.controller.model.image

import com.airbnb.epoxy.EpoxyModelClass
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowImageItemBinding
import com.zerir.robusta.domain.model.Image
import com.zerir.robusta.presentation.controller.ViewBindingKotlinModel

@EpoxyModelClass
abstract class ImageModel(
    private val imageData: Image,
    private val imageListener: (image: Image) -> Unit,
) : ViewBindingKotlinModel<RowImageItemBinding>(R.layout.row_image_item) {

    override fun RowImageItemBinding.bind() {
        dataItem.setData(imageData.previewURL, null, null)
        root.setOnClickListener { imageListener(imageData) }
    }

}