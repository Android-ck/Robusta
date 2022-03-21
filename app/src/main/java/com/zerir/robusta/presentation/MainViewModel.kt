package com.zerir.robusta.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tinder.StateMachine
import com.zerir.robusta.domain.Repository
import com.zerir.robusta.presentation.state.*
import com.zerir.robusta.presentation.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val stateOfImage = getImageState()

    private val stateOfLaunch = getLaunchState()

    private val _imageState: MutableLiveData<ImageState> = MutableLiveData(stateOfImage.state)
    val imageState: LiveData<ImageState> get() = _imageState

    private val _imageSideEffect: SingleLiveEvent<ImageSideEffect> = SingleLiveEvent()
    val imageSideEffect: LiveData<ImageSideEffect> get() = _imageSideEffect

    private val _launchState: MutableLiveData<LaunchState> = MutableLiveData(stateOfLaunch.state)
    val launchState: LiveData<LaunchState> get() = _launchState

    private val _launchSideEffect: SingleLiveEvent<LaunchSideEffect> = SingleLiveEvent()
    val launchSideEffect: LiveData<LaunchSideEffect> get() = _launchSideEffect

    private var lastQuery = ""

    init {
        loadLaunches()
    }

    fun refreshImages() = loadImages(lastQuery, true)

    fun refreshLaunches() = loadLaunches()

    fun loadImages(query: String, forceRefresh: Boolean = false) {
        if (query != lastQuery || forceRefresh) {
            manageImageStateEvent(ImageEvent.OnLoadImages)
            lastQuery = query
            repository.requestImages(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data -> manageImageStateEvent(ImageEvent.OnRetrieveImagesData(data)) },
                    { error -> manageImageStateEvent(ImageEvent.OnFailureImagesData(error)) }
                )
        }
    }

    private fun loadLaunches() {
        manageLaunchStateEvent(LaunchEvent.OnLoadLaunches)
        repository.requestLaunches()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data -> manageLaunchStateEvent(LaunchEvent.OnRetrieveLaunchesData(data.data?.launches?.launches)) },
                { error -> manageLaunchStateEvent(LaunchEvent.OnFailureLaunchesData(error)) }
            )
    }

    fun manageImageStateEvent(event: ImageEvent) {
        when (val transition = stateOfImage.transition(event)) {
            is StateMachine.Transition.Valid -> {
                transition.sideEffect?.let {
                    _imageSideEffect.value = it
                }
                _imageState.value = transition.toState
            }
            is StateMachine.Transition.Invalid -> {
                handleImageInvalidTransition(transition)
            }
        }
    }

    private fun handleImageInvalidTransition(transition: StateMachine.Transition<ImageState, ImageEvent, ImageSideEffect>) {
        val msg = "Invalid State Transition in ${stateOfImage.javaClass.name} " +
                "from: ${transition.fromState.javaClass}, event: ${transition.event.javaClass} "
        Log.e("INVALID EVENT", msg)
        throw (Exception(msg))
    }

    fun manageLaunchStateEvent(event: LaunchEvent) {
        when (val transition = stateOfLaunch.transition(event)) {
            is StateMachine.Transition.Valid -> {
                transition.sideEffect?.let {
                    _launchSideEffect.value = it
                }
                _launchState.value = transition.toState
            }
            is StateMachine.Transition.Invalid -> {
                handleLaunchInvalidTransition(transition)
            }
        }
    }

    private fun handleLaunchInvalidTransition(transition: StateMachine.Transition<LaunchState, LaunchEvent, LaunchSideEffect>) {
        val msg = "Invalid State Transition in ${stateOfImage.javaClass.name} " +
                "from: ${transition.fromState.javaClass}, event: ${transition.event.javaClass} "
        Log.e("INVALID EVENT", msg)
        throw (Exception(msg))
    }

}