package com.zerir.robusta.di

import androidx.lifecycle.LiveData
import com.zerir.calendarview.adapterData.DayItem
import com.zerir.robusta.LaunchListQuery
import com.zerir.robusta.data.RepositoryImpl
import com.zerir.robusta.data.remote.RemoteDataSource
import com.zerir.robusta.data.remote.RemoteDataSourceImpl
import com.zerir.robusta.data.remote.api.ImageApi
import com.zerir.robusta.data.remote.clinets.apollo.ApolloClientBuilder
import com.zerir.robusta.data.remote.clinets.retrofit.RetrofitClientBuilder
import com.zerir.robusta.domain.Repository
import com.zerir.robusta.domain.model.Image
import com.zerir.robusta.presentation.MainViewModel
import com.zerir.robusta.presentation.controller.MainController
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClientBuilder() }

    single { get<RetrofitClientBuilder>().build(ImageApi::class.java) }

    single { ApolloClientBuilder() }

    single { get<ApolloClientBuilder>().apolloClient }

    single<RemoteDataSource> { RemoteDataSourceImpl(imageApi = get(), apolloClient = get()) }

    single<Repository> { RepositoryImpl(remoteDataSource = get()) }

    viewModel { MainViewModel(repository = get()) }

    factory { (calendarListener: (selectedDay: LiveData<DayItem>) -> Unit,
                  imageListener: (image: Image) -> Unit,
                  launchListener: (launch: LaunchListQuery.Launch) -> Unit,
              ) -> MainController(calendarListener, imageListener, launchListener)
    }
}