package com.zerir.robusta.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.OrientationHelper.VERTICAL
import com.airbnb.epoxy.EpoxyRecyclerView
import com.zerir.calendarview.adapterData.DayItem
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
        parametersOf(::observeCalendar, ::onImageClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        imagesObserve()
    }

    private fun setupRecyclerView() {
        findViewById<EpoxyRecyclerView>(R.id.images_rv).apply {
            setController(mainController)
            addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))
            /** request controller data with empty list */
            mainController.requestModelBuild()
        }
    }

    private fun imagesObserve() {
        viewModel.images.observe(this) { data ->
            val images = data.first
            val error = data.second
            images?.let { list ->
                /** add horizontal images */
                mainController.horizontalImages = list.take(10)
                /** add all images */
                mainController.images = list.reversed()
                /** request controller data with new data */
                mainController.requestModelBuild()
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

}