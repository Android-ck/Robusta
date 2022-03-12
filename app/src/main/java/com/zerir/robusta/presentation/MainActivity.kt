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
import com.zerir.robusta.presentation.controller.MainController
import com.zerir.robusta.presentation.utils.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private var toaster: Toast? = null

    private val mainController by inject<MainController> {
        parametersOf(::observeCalendar, ::onImageClicked, ::onLaunchClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        imagesObserve()
        launchesObserve()
    }

    private fun setupRecyclerView() {
        findViewById<EpoxyRecyclerView>(R.id.images_rv).apply {
            setController(mainController)
            addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))
        }
    }

    private fun imagesObserve() {
        viewModel.images.observe(this) { data ->
            val images = data.first
            val error = data.second
            images?.let { list ->
                /** add all images */
                mainController.horizontalImages = list
            }
            error?.let { e ->
                toaster = toast(toaster, e.message.toString())
                toaster?.show()
            }
        }
    }

    private fun launchesObserve() {
        viewModel.launches.observe(this) { data ->
            val launches = data.first
            val error = data.second
            launches?.let { list ->
                /** add all launches */
                mainController.launches = list
            }
            error?.let { e ->
                toaster = toast(toaster, e.message.toString())
                toaster?.show()
            }
        }
    }

    private fun observeCalendar(selectedDay: LiveData<DayItem>) {
        selectedDay.observe(this@MainActivity) { day ->
            viewModel.loadImages("${day.numberInMonth * (day.month + 1)}")
        }
    }

    private fun onImageClicked(image: Image) {
        toaster = toast(toaster, image.user)
        toaster?.show()
    }

    private fun onLaunchClicked(launch: LaunchListQuery.Launch) {
        toaster = toast(toaster, launch.site.toString())
        toaster?.show()
    }

}