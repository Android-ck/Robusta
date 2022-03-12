package com.zerir.robusta.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerir.robusta.LaunchListQuery
import com.zerir.robusta.domain.Repository
import com.zerir.robusta.domain.model.Image
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val _images = MutableLiveData<Pair<List<Image>?, Throwable?>>()
    val images: LiveData<Pair<List<Image>?, Throwable?>> = _images

    private val _launches = MutableLiveData<Pair<List<LaunchListQuery.Launch?>?, Throwable?>>()
    val launches: LiveData<Pair<List<LaunchListQuery.Launch?>?, Throwable?>> = _launches

    private var lastQuery = ""

    init {
        loadLaunches()
    }

    fun loadImages(query: String) {
        if (query != lastQuery) {
            lastQuery = query
            repository.requestImages(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data -> _images.postValue(Pair(data, null)) },
                    { error -> _images.postValue(Pair(null, error)) }
                )
        }
    }

    private fun loadLaunches() {
        repository.requestLaunches()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->  _launches.postValue(Pair(data.data?.launches?.launches, null)) },
                { error -> _launches.postValue(Pair(null, error)) }
            )
    }
}