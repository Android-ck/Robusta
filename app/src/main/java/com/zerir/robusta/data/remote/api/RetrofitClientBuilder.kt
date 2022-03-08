package com.zerir.robusta.data.remote.api

import com.zerir.robusta.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClientBuilder {

    fun <Api> build(api: Class<Api>): Api = retrofitClient.create(api)

    companion object {
        private const val BASE_URL = "https://pixabay.com/api/"

        private val retrofitClient = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildRetrofitClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        private fun buildRetrofitClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(
                    Interceptor { chain ->
                        chain.proceed(
                            chain.request().newBuilder().also {
                                it.addHeader("Accept", "application/json")
                            }.build()
                        )
                    }
                ).also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }
                .build()
        }

    }

}