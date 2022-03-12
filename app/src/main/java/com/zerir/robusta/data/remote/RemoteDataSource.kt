package com.zerir.robusta.data.remote

import com.apollographql.apollo3.api.ApolloResponse
import com.zerir.robusta.LaunchListQuery
import com.zerir.robusta.data.remote.response.RetrieveResponse
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {

    fun requestImages(query: String): Single<RetrieveResponse>

    fun requestLaunches(): Single<ApolloResponse<LaunchListQuery.Data>>

}