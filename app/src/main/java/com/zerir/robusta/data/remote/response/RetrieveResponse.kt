package com.zerir.robusta.data.remote.response

import com.zerir.robusta.domain.model.Image

data class RetrieveResponse(
    val total: Int = 0,
    val totalHits: Int = 0,
    val hits: List<Image> = listOf(),
)
