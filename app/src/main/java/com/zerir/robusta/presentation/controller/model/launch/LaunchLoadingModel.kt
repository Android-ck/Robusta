package com.zerir.robusta.presentation.controller.model.launch

import com.airbnb.epoxy.EpoxyModelClass
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowLaunchesLoadingItemBinding
import com.zerir.robusta.presentation.controller.ViewBindingKotlinModel

@EpoxyModelClass
abstract class LaunchLoadingModel : ViewBindingKotlinModel<RowLaunchesLoadingItemBinding>(R.layout.row_launches_loading_item) {

    override fun RowLaunchesLoadingItemBinding.bind() {

    }

}