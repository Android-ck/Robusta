package com.zerir.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat

class ImageItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val _circleImage: CircleImage
    private val _headerDesc: HeaderDesc

    init {
        LayoutInflater.from(context).inflate(R.layout.view_image_item, this, true)

        _circleImage = findViewById(R.id.photo_iv)
        _headerDesc = findViewById(R.id.headerDesc)

        val typedArrayImage = context.theme.obtainStyledAttributes(attrs, R.styleable.Image, defStyleAttr, 0)

        with(typedArrayImage) {
            /** set resource */
            val androidSrc = getDrawable(R.styleable.Image_android_src)
            _circleImage.addSrc(androidSrc)

            /** load image */
            val url = getString(R.styleable.Image_url)
            _circleImage.addUrl(url)
        }

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.HeaderDesc, defStyleAttr, 0)

        with(typedArray) {

            /** text */
            val headerText = getString(R.styleable.HeaderDesc_headerText)
            val descText = getString(R.styleable.HeaderDesc_descText)

            if(headerText.isNullOrBlank() && descText.isNullOrBlank()) {
                _headerDesc.visibility = GONE
            }

            _headerDesc.setHeaderText(headerText)
            _headerDesc.setDescText(descText)

            /** color */
            _headerDesc.setHeaderTextColor(getColor(R.styleable.HeaderDesc_headerTextColor, -1))
            _headerDesc.setDescTextColor(getColor(R.styleable.HeaderDesc_descTextColor, -1))

            /** size */
            _headerDesc.setHeaderTextSize(getDimension(R.styleable.HeaderDesc_headerTextSize, -1f))
            _headerDesc.setDescTextSize(getDimension(R.styleable.HeaderDesc_descTextSize, -1f))

            /** style */
            _headerDesc.setHeaderTextStyle(getInt(R.styleable.HeaderDesc_headerTextStyle, -1))
            _headerDesc.setDescTextStyle(getInt(R.styleable.HeaderDesc_descTextStyle, -1))

            /** static transparent background */
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))

            recycle()
        }
    }

    fun setData(url: String?, headerText: String?, descText: String?) {
        _circleImage.addUrl(url)
        if(headerText.isNullOrBlank() && descText.isNullOrBlank()) {
            _headerDesc.visibility = GONE
        } else {
            _headerDesc.visibility = VISIBLE
        }
        _headerDesc.setHeaderText(headerText)
        _headerDesc.setDescText(descText)
    }

}