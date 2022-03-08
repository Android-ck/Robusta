package com.zerir.robusta.di

import com.zerir.robusta.data.RepositoryImpl
import com.zerir.robusta.data.remote.RemoteDataSource
import com.zerir.robusta.data.remote.RemoteDataSourceImpl
import com.zerir.robusta.data.remote.api.ImageApi
import com.zerir.robusta.data.remote.api.RetrofitClientBuilder
import com.zerir.robusta.domain.Repository
import com.zerir.robusta.presentation.MainViewModel
import com.zerir.robusta.presentation.adapter.ImageAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClientBuilder() }

    single { get<RetrofitClientBuilder>().build(ImageApi::class.java) }

    single<RemoteDataSource> { RemoteDataSourceImpl(imageApi = get()) }

    single<Repository> { RepositoryImpl(remoteDataSource = get()) }

    viewModel { MainViewModel(repository = get()) }

    factory { ImageAdapter() }
}