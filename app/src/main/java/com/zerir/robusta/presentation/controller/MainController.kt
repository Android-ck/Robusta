package com.zerir.robusta.presentation.controller

import androidx.lifecycle.LiveData
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.zerir.calendarview.adapterData.DayItem
import com.zerir.robusta.R
import com.zerir.robusta.databinding.RowCalendarItemBinding
import com.zerir.robusta.databinding.RowImageItemBinding
import com.zerir.robusta.domain.model.Image

class MainController(
    private val calendarListener: (selectedDay: LiveData<DayItem>) -> Unit,
    private val imageListener: (image: Image) -> Unit,
) : EpoxyController() {

    var horizontalImages: List<Image> = emptyList()

    var images: List<Image> = emptyList()

    override fun buildModels() {
        /** add calendar */
        CalendarItem(calendarListener).id("Calendar").addTo(this)
        /** add horizontal images */
        CarouselModel_()
            .id("Horizontal")
            .models(horizontalImages.map {
                HorizontalImageItem(it, imageListener).id(it.id)
            })
            .addTo(this)
        /** add all images */
        images.forEach {
            ImageItem(it, imageListener).id(it.id).addTo(this)
        }
    }

    class CalendarItem(private val calendarListener: (selectedDay: LiveData<DayItem>) -> Unit) :
        ViewBindingKotlinModel<RowCalendarItemBinding>(R.layout.row_calendar_item) {

        override fun RowCalendarItemBinding.bind() {
            calendarListener(calendar.selectedDay)
        }
    }

    class HorizontalImageItem(
        private val imageData: Image,
        private val imageListener: (image: Image) -> Unit,
    ) : ViewBindingKotlinModel<RowImageItemBinding>(R.layout.row_image_item) {

        override fun RowImageItemBinding.bind() {
            dataItem.setData(imageData.previewURL, null, null)
            root.setOnClickListener { imageListener(imageData) }
        }

    }

    class ImageItem(
        private val imageData: Image,
        private val imageListener: (image: Image) -> Unit,
    ) : ViewBindingKotlinModel<RowImageItemBinding>(R.layout.row_image_item) {

        override fun RowImageItemBinding.bind() {
            dataItem.setData(imageData.previewURL, imageData.user, imageData.tags)
            root.setOnClickListener { imageListener(imageData) }
        }

    }

}