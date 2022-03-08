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
import com.zerir.robusta.presentation.adapter.data.CalendarListener
import com.zerir.robusta.presentation.adapter.data.DataItem
import com.zerir.robusta.presentation.adapter.data.ImageListener
import com.zerir.robusta.presentation.utils.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ImageListener, CalendarListener {

    private val viewModel: MainViewModel by viewModel()

    private var toaster: Toast? = null
    private val imageAdapter by inject<ImageAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        imagesObserve()

        /** adding calendar  */
        imageAdapter.submitList(listOf(DataItem.CalendarItem))
    }

    private fun setupRecyclerView() {
        findViewById<RecyclerView>(R.id.images_rv).apply {
            adapter = imageAdapter.apply {
                registerListeners(this@MainActivity, this@MainActivity)
            }
            addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun imagesObserve() {
        viewModel.images.observe(this) { data ->
            val images = data.first
            val error = data.second
            images?.let { list ->
                val items = mutableListOf<DataItem>(DataItem.CalendarItem)
                items.addAll(list.map { DataItem.ImageItem(it) })
                imageAdapter.submitList(items)
            }
            error?.let { e ->
                toaster = toast(toaster, e.message.toString())
                toaster?.show()
            }
        }
    }

    override fun onImageClicked(image: Image) {
        toaster = toast(toaster, image.user)
        toaster?.show()
    }

    override fun observeCalendar(selectedDay: LiveData<DayItem>) {
        selectedDay.observe(this@MainActivity) { day ->
            viewModel.loadImages("${day.numberInMonth}")
        }
    }

}