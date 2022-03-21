package com.zerir.robusta.presentation.state

import com.tinder.StateMachine
import com.zerir.robusta.domain.model.Image

sealed class ImageState {
    object UnInitialized : ImageState()

    object LoadingImagesData : ImageState()

    data class RetrieveImagesData(val images: List<Image>) : ImageState()

    data class FailureImagesData(val error: Throwable) : ImageState()
}

sealed class ImageEvent {
    object OnLoadImages : ImageEvent()

    data class OnRetrieveImagesData(val images: List<Image>) : ImageEvent()

    data class OnFailureImagesData(val error: Throwable) : ImageEvent()

    data class OnImageClicked(val image: Image) : ImageEvent()
}

sealed class ImageSideEffect {
    data class ShowToast(val message: String) : ImageSideEffect()
}

fun getImageState() = StateMachine.create<ImageState, ImageEvent, ImageSideEffect> {
    initialState(ImageState.UnInitialized)
    /** UnInitialized */
    state<ImageState.UnInitialized> {
        on<ImageEvent.OnLoadImages> { transitionTo(ImageState.LoadingImagesData) }
    }
    /** LoadingImagesData */
    state<ImageState.LoadingImagesData> {
        on<ImageEvent.OnRetrieveImagesData> { event -> transitionTo(ImageState.RetrieveImagesData(event.images)) }
        on<ImageEvent.OnFailureImagesData> { event -> transitionTo(ImageState.FailureImagesData(event.error)) }
        on<ImageEvent.OnLoadImages> { transitionTo(this) }
    }
    /** RetrieveImagesData */
    state<ImageState.RetrieveImagesData> {
        on<ImageEvent.OnImageClicked> { event -> transitionTo(this, ImageSideEffect.ShowToast(event.image.user)) }
        on<ImageEvent.OnLoadImages> { transitionTo(ImageState.LoadingImagesData) }
    }
    /** FailureImagesData */
    state<ImageState.FailureImagesData> {
        on<ImageEvent.OnLoadImages> { transitionTo(ImageState.LoadingImagesData) }
    }
}