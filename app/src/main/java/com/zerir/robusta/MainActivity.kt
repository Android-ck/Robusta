package com.zerir.robusta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zerir.calendarview.CalendarView

class MainActivity : AppCompatActivity() {

    private var toaster: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<CalendarView>(R.id.calendar).apply {
            selectedDay.observe(this@MainActivity) { day ->
                toaster?.cancel()
                toaster = Toast.makeText(
                    this@MainActivity,
                    "${day.numberInMonth}, ${day.nameInWeek}",
                    Toast.LENGTH_LONG
                )
                toaster?.show()
            }
        }

    }
}