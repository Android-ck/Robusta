package com.zerir.robusta.data.remote

import com.zerir.robusta.data.remote.api.ImageApi
import com.zerir.robusta.data.remote.response.RetrieveResponse
import io.reactivex.rxjava3.core.Single

class RemoteDataSourceImpl(
    private val imageApi: ImageApi,
) : RemoteDataSource {

    override fun requestImages(query: String): Single<RetrieveResponse> =
        imageApi.retrieveImages(query = query)

}