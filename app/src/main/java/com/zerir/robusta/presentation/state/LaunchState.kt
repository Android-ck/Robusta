package com.zerir.robusta.presentation.state

import com.tinder.StateMachine
import com.zerir.robusta.LaunchListQuery

sealed class LaunchState {
    object UnInitialized : LaunchState()

    object LoadingLaunchesData : LaunchState()

    data class RetrieveLaunchesData(val launches: List<LaunchListQuery.Launch?>?) : LaunchState()

    data class FailureLaunchesData(val error: Throwable) : LaunchState()
}

sealed class LaunchEvent {
    object OnLoadLaunches : LaunchEvent()

    data class OnRetrieveLaunchesData(val launches: List<LaunchListQuery.Launch?>?) : LaunchEvent()

    data class OnFailureLaunchesData(val error: Throwable) : LaunchEvent()

    data class OnLaunchClicked(val launch: LaunchListQuery.Launch) : LaunchEvent()
}

sealed class LaunchSideEffect {
    data class ShowToast(val message: String) : LaunchSideEffect()
}

fun getLaunchState() = StateMachine.create<LaunchState, LaunchEvent, LaunchSideEffect> {
    initialState(LaunchState.UnInitialized)
    /** UnInitialized */
    state<LaunchState.UnInitialized> {
        on<LaunchEvent.OnLoadLaunches> { transitionTo(LaunchState.LoadingLaunchesData) }
    }
    /** LoadingLaunchesData */
    state<LaunchState.LoadingLaunchesData> {
        on<LaunchEvent.OnRetrieveLaunchesData> { event -> transitionTo(LaunchState.RetrieveLaunchesData(event.launches)) }
        on<LaunchEvent.OnFailureLaunchesData> { event -> transitionTo(LaunchState.FailureLaunchesData(event.error)) }
    }
    /** RetrieveLaunchesData */
    state<LaunchState.RetrieveLaunchesData> {
        on<LaunchEvent.OnLaunchClicked> { event -> transitionTo(this, LaunchSideEffect.ShowToast(event.launch.site.toString())) }
        on<LaunchEvent.OnLoadLaunches> { transitionTo(LaunchState.LoadingLaunchesData) }
    }
    /** FailureLaunchesData */
    state<LaunchState.FailureLaunchesData> {
        on<LaunchEvent.OnLoadLaunches> { transitionTo(LaunchState.LoadingLaunchesData) }
    }
}