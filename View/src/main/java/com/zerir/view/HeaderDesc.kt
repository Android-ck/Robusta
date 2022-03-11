package com.zerir.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

class HeaderDesc @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val _header: TextView
    private val _desc: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_header_desc, this, true)

        _header = findViewById(R.id.header)
        _desc = findViewById(R.id.desc)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.HeaderDesc, defStyleAttr, 0)

        with(typedArray) {

            /** text */
            setHeaderText(getString(R.styleable.HeaderDesc_headerText))
            setDescText(getString(R.styleable.HeaderDesc_descText))

            /** color */
            setHeaderTextColor(getColor(R.styleable.HeaderDesc_headerTextColor, -1))
            setDescTextColor(getColor(R.styleable.HeaderDesc_descTextColor, -1))

            /** size */
            setHeaderTextSize(getDimension(R.styleable.HeaderDesc_headerTextSize, -1f))
            setDescTextSize(getDimension(R.styleable.HeaderDesc_descTextSize, -1f))

            /** style */
            setHeaderTextStyle(getInt(R.styleable.HeaderDesc_headerTextStyle, -1))
            setDescTextStyle(getInt(R.styleable.HeaderDesc_descTextStyle, -1))

            /** static transparent background */
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))

            /** static max lines */
            _header.maxLines = 1
            _desc.maxLines = 3

            recycle()
        }
    }

    fun setHeaderText(text: String?) { _header.text = text }

    fun setDescText(text: String?) { _desc.text = text }

    fun setHeaderTextColor(@ColorInt color: Int) { if(color != -1) { _header.setTextColor(color) }}

    fun setDescTextColor(@ColorInt color: Int) { if(color != -1) { _desc.setTextColor(color) }}

    fun setHeaderTextSize(size: Float) { if(size != -1f) { _header.textSize = size }}

    fun setDescTextSize(size: Float) { if(size != -1f) { _desc.textSize = size }}

    fun setHeaderTextStyle(style: Int) { if(style != -1) { _header.setTypeface(_header.typeface, style) }}

    fun setDescTextStyle(style: Int) { if(style != -1) { _desc.setTypeface(_desc.typeface, style) }}

}