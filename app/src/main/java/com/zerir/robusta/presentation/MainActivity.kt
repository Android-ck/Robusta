package com.zerir.robusta.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.OrientationHelper.VERTICAL
import com.airbnb.epoxy.EpoxyRecyclerView
import com.zerir.calendarview.adapterData.DayItem
import com.zerir.robusta.LaunchListQuery
import com.zerir.robusta.R
import com.zerir.robusta.domain.model.Image
import com.zerir.robusta.presentation.controller.Data
import com.zerir.robusta.presentation.controller.MainController
import com.zerir.robusta.presentation.state.*
import com.zerir.robusta.presentation.utils.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private var toaster: Toast? = null

    private val mainController by inject<MainController> {
        parametersOf(
            ::observeCalendar,
            ::onImageClicked,
            ::onLaunchClicked,
            ::onRefreshImages,
            ::onRefreshLaunches
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        collectImageState()
        collectImageSideEffect()
        collectLaunchState()
        collectLaunchSideEffect()
    }

    private fun setupRecyclerView() {
        findViewById<EpoxyRecyclerView>(R.id.images_rv).apply {
            setController(mainController)
            addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))
        }
    }

    private fun collectImageState() = viewModel.imageState.observe(this) {
        mainController.imagesData = when (val state = it) {
            is ImageState.FailureImagesData -> Data(error = state.error)
            ImageState.LoadingImagesData -> Data(isLoading = true)
            is ImageState.RetrieveImagesData -> Data(data = state.images)
            ImageState.UnInitialized -> Data()
        }
    }

    private fun collectImageSideEffect() = viewModel.imageSideEffect.observe(this) {
        if (it is ImageSideEffect.ShowToast) {
            toaster = toast(toaster, it.message)
            toaster?.show()
        }
    }

    private fun collectLaunchState() = viewModel.launchState.observe(this) {
        mainController.launchesData = when (val state = it) {
            is LaunchState.FailureLaunchesData -> Data(error = state.error)
            LaunchState.LoadingLaunchesData -> Data(isLoading = true)
            is LaunchState.RetrieveLaunchesData -> Data(data = state.launches)
            LaunchState.UnInitialized -> Data()
        }
    }

    private fun collectLaunchSideEffect() = viewModel.launchSideEffect.observe(this) {
        if (it is LaunchSideEffect.ShowToast) {
            toaster = toast(toaster, it.message)
            toaster?.show()
        }
    }

    private fun observeCalendar(selectedDay: LiveData<DayItem>) {
        selectedDay.observe(this@MainActivity) { day ->
            viewModel.loadImages("${day.numberInMonth * (day.month + 1)}")
        }
    }

    private fun onImageClicked(image: Image) {
        viewModel.manageImageStateEvent(ImageEvent.OnImageClicked(image))
    }

    private fun onLaunchClicked(launch: LaunchListQuery.Launch) {
        viewModel.manageLaunchStateEvent(LaunchEvent.OnLaunchClicked(launch))
    }

    private fun onRefreshImages() {
        viewModel.refreshImages()
    }

    private fun onRefreshLaunches() {
        viewModel.refreshLaunches()
    }

}