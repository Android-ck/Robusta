package com.zerir.robusta.domain

import com.zerir.robusta.domain.model.Image
import io.reactivex.rxjava3.core.Observable

interface Repository {

    fun requestImages(): Observable<List<Image>>

}