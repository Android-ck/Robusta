package com.zerir.robusta.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.rx3.rxSingle
import com.zerir.robusta.LaunchListQuery
import com.zerir.robusta.data.remote.api.ImageApi
import com.zerir.robusta.data.remote.response.RetrieveResponse
import io.reactivex.rxjava3.core.Single

class RemoteDataSourceImpl(
    private val imageApi: ImageApi,
    private val apolloClient: ApolloClient,
) : RemoteDataSource {

    override fun requestImages(query: String): Single<RetrieveResponse> =
        imageApi.retrieveImages(query = query)

    override fun requestLaunches(): Single<ApolloResponse<LaunchListQuery.Data>> =
        apolloClient.query(LaunchListQuery()).rxSingle()

}