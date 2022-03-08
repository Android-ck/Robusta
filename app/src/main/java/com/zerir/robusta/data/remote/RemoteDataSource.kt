package com.zerir.robusta.data.remote

import com.zerir.robusta.data.remote.response.RetrieveResponse
import io.reactivex.rxjava3.core.Observable

interface RemoteDataSource {

    fun requestImages(query: String): Observable<RetrieveResponse>

}