package com.zerir.robusta.presentation.controller

import androidx.lifecycle.LiveData
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.zerir.calendarview.adapterData.DayItem
import com.zerir.robusta.LaunchListQuery
import com.zerir.robusta.domain.model.Image
import com.zerir.robusta.presentation.controller.model.calendar.CalendarModel_
import com.zerir.robusta.presentation.controller.model.image.ImageErrorModel_
import com.zerir.robusta.presentation.controller.model.image.ImageModel_
import com.zerir.robusta.presentation.controller.model.image.ImagesEmptyModel_
import com.zerir.robusta.presentation.controller.model.image.ImagesLoadingModel_
import com.zerir.robusta.presentation.controller.model.launch.LaunchEmptyModel_
import com.zerir.robusta.presentation.controller.model.launch.LaunchErrorModel_
import com.zerir.robusta.presentation.controller.model.launch.LaunchLoadingModel_
import com.zerir.robusta.presentation.controller.model.launch.LaunchModel_

class MainController(
    private val calendarListener: (selectedDay: LiveData<DayItem>) -> Unit,
    private val imageListener: (image: Image) -> Unit,
    private val launchListener: (launch: LaunchListQuery.Launch) -> Unit,
    private val onRefreshImages: () -> Unit,
    private val onRefreshLaunches: () -> Unit,
) : EpoxyController() {

    var imagesData: Data<Image> = Data()
        set(value) {
            field = value
            requestModelBuild()
        }

    var launchesData: Data<LaunchListQuery.Launch?> = Data()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        /** add calendar */
        CalendarModel_(calendarListener).id("CALENDAR").addTo( this)
        /** add images */
        when {
            imagesData.isLoading -> { ImagesLoadingModel_().id("IMAGES_LOADING").addTo(this) }
            imagesData.error != null -> { ImageErrorModel_(onRefreshImages).id("IMAGES_ERROR").addTo(this) }
            imagesData.data != null -> {
                if(imagesData.data.isNullOrEmpty()) ImagesEmptyModel_().id("IMAGES_EMPTY").addTo(this)
                else {
                    CarouselModel_()
                        .id("IMAGES")
                        .models(imagesData.data!!.map {
                            ImageModel_(it, imageListener).id(it.id)
                        })
                        .addTo(this)
                }
            }
        }
        /** add launches */
        when {
            launchesData.isLoading -> { LaunchLoadingModel_().id("LAUNCHES_LOADING").addTo(this) }
            launchesData.error != null -> { LaunchErrorModel_(onRefreshLaunches).id("LAUNCHES_ERROR").addTo(this) }
            launchesData.data != null -> {
                if(launchesData.data.isNullOrEmpty()) LaunchEmptyModel_().id("LAUNCHES_EMPTY").addTo(this)
                else {
                    launchesData.data!!.filterNotNull().forEach {
                        LaunchModel_(it, launchListener).id(it.id).addTo(this)
                    }
                }
            }
        }
    }
}

data class Data<T>(
    val isLoading: Boolean = false,
    val data: List<T>? = null,
    val error: Throwable? = null,
)