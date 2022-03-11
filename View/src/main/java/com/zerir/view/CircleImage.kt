package com.zerir.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class CircleImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val _shape: CardView
    private val _networkImage: NetworkImage

    init {
        LayoutInflater.from(context).inflate(R.layout.view_circle_image, this, true)

        _shape = findViewById(R.id.shape)
        _networkImage = findViewById(R.id.networkImage)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.Image, defStyleAttr, 0)

        with(typedArray) {
            /** set resource */
            val androidSrc = getDrawable(R.styleable.Image_android_src)
            _networkImage.addSrc(androidSrc)

            /** load image */
            val url = getString(R.styleable.Image_url)
            _networkImage.addUrl(url)

            /** set radius to make image circle */
            val height = getDimension(R.styleable.Image_android_layout_height, 0f)
            _shape.radius = height

            /** static transparent background */
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))

            recycle()
        }
    }

    fun addUrl(url: String?) {
        _networkImage.addUrl(url)
    }

    fun addSrc(drawable: Drawable?) {
        _networkImage.addSrc(drawable)
    }

}