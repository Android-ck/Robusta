package com.zerir.robusta.data

import com.zerir.robusta.data.remote.RemoteDataSource
import com.zerir.robusta.domain.Repository
import com.zerir.robusta.domain.model.Image
import io.reactivex.rxjava3.core.Single

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : Repository {

    override fun requestImages(query: String) : Single<List<Image>> {
        val response = remoteDataSource.requestImages(query)
        return response.map { res -> res.hits }
    }

}