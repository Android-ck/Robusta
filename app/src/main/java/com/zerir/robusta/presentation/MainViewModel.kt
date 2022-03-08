package com.zerir.robusta.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerir.robusta.domain.Repository
import com.zerir.robusta.domain.model.Image
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val _images = MutableLiveData<Pair<List<Image>?, Throwable?>>()
    val images: LiveData<Pair<List<Image>?, Throwable?>> = _images

    private val compositeDisposable = CompositeDisposable()

    private var lastQuery = ""

    fun loadImages(query: String) {
        if(query != lastQuery) {
            lastQuery = query
            compositeDisposable.add(
                repository.requestImages(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(initImageObserver())
            )
        }
    }

    private fun initImageObserver(): DisposableObserver<List<Image>> =
        object : DisposableObserver<List<Image>>() {
            override fun onNext(data: List<Image>) { _images.postValue(Pair(data, null)) }

            override fun onError(e: Throwable) { _images.postValue(Pair(null, e)) }

            override fun onComplete() {}
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}