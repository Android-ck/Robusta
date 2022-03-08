package com.zerir.calendarview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zerir.calendarview.manager.CalendarManagerImpl

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : CardView(context, attrs, defStyleAttr) {

    private val _manager = CalendarManagerImpl()

    private val _card: CardView
    private val _changeMonthForwardIb: ImageView
    private val _changeMonthBackwardIb: ImageView
    private val _monthYearTv: TextView
    private val _daysListRv: RecyclerView

    val selectedDay get() = _manager.selectedDay

    init {
        LayoutInflater.from(context).inflate(R.layout.view_calendar, this, true)

        _card = findViewById(R.id.card)
        _changeMonthForwardIb = findViewById(R.id.forwardMonth)
        _changeMonthBackwardIb = findViewById(R.id.backMonth)
        _monthYearTv = findViewById(R.id.month)
        _daysListRv = findViewById(R.id.daysList_rv)

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            androidx.cardview.R.styleable.CardView,
            defStyleAttr,
            0
        )

        with(typedArray) {
            /** default values */
            setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            elevation = 0f
            radius = 0f
            /** set elevation always to 0 */
            if (hasValue(androidx.cardview.R.styleable.CardView_cardElevation)) {
                elevation = 0f
            }
            /** set radius always to 0 */
            if (hasValue(androidx.cardview.R.styleable.CardView_cardCornerRadius)) {
                radius = 0f
            }
            /** change color of card */
            if (hasValue(androidx.cardview.R.styleable.CardView_cardBackgroundColor)) {
                _card.setCardBackgroundColor(getColor(androidx.cardview.R.styleable.CardView_cardBackgroundColor, 0))
            }
            typedArray.recycle()
            /** setup views */
            setupView()
        }

        val typedArrayCalendar = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CalendarView,
            defStyleAttr,
            0
        )

        with(typedArrayCalendar) {
            /** change color of day item background */
            if (hasValue(R.styleable.CalendarView_defaultDayBackgroundColor)) {
                _manager.daysAdapter.changeColor(getColorStateList(R.styleable.CalendarView_defaultDayBackgroundColor))
            }
            typedArrayCalendar.recycle()
        }
    }

    private fun setupView() {
        _daysListRv.adapter = _manager.daysAdapter

        _changeMonthForwardIb.setOnClickListener {
            _monthYearTv.text = _manager.changeMonth(true)
            _daysListRv.smoothScrollToPosition(_manager.scrollTo)
        }
        _changeMonthBackwardIb.setOnClickListener {
            _monthYearTv.text = _manager.changeMonth(false)
            _daysListRv.smoothScrollToPosition(_manager.scrollTo)
        }

        _monthYearTv.text = _manager.monthWithYearName

        _daysListRv.smoothScrollToPosition(_manager.scrollTo)
    }

}