package com.zerir.robusta.presentation.controller.model.launch

import com.airbnb.epoxy.EpoxyModelClass
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowLaunchesErrorItemBinding
import com.zerir.robusta.presentation.controller.ViewBindingKotlinModel

@EpoxyModelClass
abstract class LaunchErrorModel(
    private val onRefreshLaunches: () -> Unit,
) : ViewBindingKotlinModel<RowLaunchesErrorItemBinding>(R.layout.row_launches_error_item) {

    override fun RowLaunchesErrorItemBinding.bind() {
        errorMsgTv.setOnClickListener { onRefreshLaunches() }
    }

}