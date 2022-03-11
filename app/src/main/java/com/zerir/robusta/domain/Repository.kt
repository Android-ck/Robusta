package com.zerir.robusta.domain

import com.zerir.robusta.domain.model.Image
import io.reactivex.rxjava3.core.Single

interface Repository {

    fun requestImages(query: String): Single<List<Image>>

}