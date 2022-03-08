package com.zerir.robusta.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.OrientationHelper.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.zerir.calendarview.adapterData.DayItem
import com.zerir.robusta.R
import com.zerir.robusta.domain.model.Image
import com.zerir.robusta.presentation.adapter.ImageAdapter
import com.zerir.robusta.presentation.adapter.data.DataItem
import com.zerir.robusta.presentation.utils.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private var toaster: Toast? = null
    private val imageAdapter by inject<ImageAdapter>()

    private val calendarItem = DataItem.CalendarItem(::observeCalendar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        imagesObserve()

        /** adding calendar */
        imageAdapter.submitList(listOf(calendarItem))
    }

    private fun setupRecyclerView() {
        findViewById<RecyclerView>(R.id.images_rv).apply {
            adapter = imageAdapter
            addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun imagesObserve() {
        viewModel.images.observe(this) { data ->
            val images = data.first
            val error = data.second
            images?.let { list ->
                /** adding calendar  */
                val items = mutableListOf<DataItem>(calendarItem)
                /** adding some items as horizontal  */
                val horizontalItems = list.take(10).map { DataItem.ImageItem(it, ::onImageClicked) }
                items.add(DataItem.HorizontalImagesItem(horizontalItems))
                /** adding all items as vertical  */
                items.addAll(list.reversed().map { DataItem.ImageItem(it, ::onImageClicked) })
                imageAdapter.submitList(items)
            }
            error?.let { e ->
                toaster = toast(toaster, e.message.toString())
                toaster?.show()
            }
        }
    }

    private fun observeCalendar(selectedDay: LiveData<DayItem>) {
        selectedDay.observe(this@MainActivity) { day ->
            viewModel.loadImages("${day.numberInMonth}")
        }
    }

    private fun onImageClicked(image: Image) {
        toaster = toast(toaster, image.user)
        toaster?.show()
    }

}