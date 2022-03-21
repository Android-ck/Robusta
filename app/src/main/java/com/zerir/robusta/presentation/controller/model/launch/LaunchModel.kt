package com.zerir.robusta.presentation.controller.model.launch

import com.airbnb.epoxy.EpoxyModelClass
import com.zerir.robusta.LaunchListQuery
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowImageItemBinding
import com.zerir.robusta.presentation.controller.ViewBindingKotlinModel

@EpoxyModelClass
abstract class LaunchModel(
    private val launch: LaunchListQuery.Launch,
    private val launchListener: (launch: LaunchListQuery.Launch) -> Unit,
) : ViewBindingKotlinModel<RowImageItemBinding>(R.layout.row_image_item) {

    override fun RowImageItemBinding.bind() {
        dataItem.setData(launch.mission?.missionPatch, launch.id, launch.site)
        root.setOnClickListener { launchListener(launch) }
    }

}