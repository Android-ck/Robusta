package com.zerir.robusta.presentation.adapter.data

import androidx.lifecycle.LiveData
import com.zerir.calendarview.adapterData.DayItem
import com.zerir.robusta.domain.model.Image

sealed class DataItem(val id: String) {

    data class CalendarItem(
        val observeDayItem: (selectedDat: LiveData<DayItem>) -> Unit
    ) : DataItem("CalendarItem")

    data class HorizontalImagesItem(val images: List<ImageItem>) : DataItem("Horizontal Item")

    data class ImageItem(
        val image: Image,
        val onImageClicked: (image: Image) -> Unit
    ) : DataItem(image.id)
}
