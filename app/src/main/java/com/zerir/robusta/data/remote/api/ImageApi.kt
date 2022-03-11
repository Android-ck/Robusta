package com.zerir.robusta.data.remote.api

import com.zerir.robusta.BuildConfig
import com.zerir.robusta.data.remote.response.RetrieveResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("?")
    fun retrieveImages(
        @Query("q") query: String,
        @Query("key") key: String = BuildConfig.APP_KEY,
    ): Single<RetrieveResponse>

}