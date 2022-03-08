package com.zerir.robusta.presentation.adapter.data

import com.zerir.robusta.domain.model.Image

sealed class DataItem(val id: String) {
    object CalendarItem : DataItem("CalendarItem")
    data class ImageItem(val image: Image): DataItem(image.id)
}
