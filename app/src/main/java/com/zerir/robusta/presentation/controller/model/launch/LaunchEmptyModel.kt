package com.zerir.robusta.presentation.controller.model.launch

import com.airbnb.epoxy.EpoxyModelClass
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowLaunchesEmptyItemBinding
import com.zerir.robusta.presentation.controller.ViewBindingKotlinModel

@EpoxyModelClass
abstract class LaunchEmptyModel : ViewBindingKotlinModel<RowLaunchesEmptyItemBinding>(R.layout.row_launches_empty_item) {

    override fun RowLaunchesEmptyItemBinding.bind() {

    }

}