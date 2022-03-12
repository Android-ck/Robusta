package com.zerir.robusta.domain

import com.apollographql.apollo3.api.ApolloResponse
import com.zerir.robusta.LaunchListQuery
import com.zerir.robusta.domain.model.Image
import io.reactivex.rxjava3.core.Single

interface Repository {

    fun requestImages(query: String): Single<List<Image>>

    fun requestLaunches(): Single<ApolloResponse<LaunchListQuery.Data>>

}