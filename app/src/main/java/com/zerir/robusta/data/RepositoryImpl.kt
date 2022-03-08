package com.zerir.robusta.data

import com.zerir.robusta.data.remote.RemoteDataSource
import com.zerir.robusta.domain.Repository
import com.zerir.robusta.domain.model.Image
import io.reactivex.rxjava3.core.Observable

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : Repository {

    override fun requestImages() : Observable<List<Image>> {
        val response = remoteDataSource.requestImages()
        return response.map { res -> res.hits }
    }

}