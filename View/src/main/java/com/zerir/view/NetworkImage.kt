package com.zerir.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

class NetworkImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val glide get() = Glide.with(context)

    private val _image: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_network_image, this, true)

        _image = findViewById(R.id.image)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.Image, defStyleAttr, 0)

        with(typedArray) {
            /** set resource */
            val androidSrc = getDrawable(R.styleable.Image_android_src)
            _image.setImageDrawable(androidSrc)

            /** load image */
            val url = getString(R.styleable.Image_url)
            loadUrl(url)

            /** static transparent background */
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        }
    }

    fun addUrl(url: String?) {
        loadUrl(url)
    }

    fun addSrc(drawable: Drawable?) {
        _image.setImageDrawable(drawable)
    }

    private fun loadUrl(url: String?) {
        url?.let {
            glide.load(url)
                .placeholder(ActivityCompat.getDrawable(context, R.drawable.ic_download))
                .error(ActivityCompat.getDrawable(context, R.drawable.ic_download_failed))
                .into(_image)
        }
    }

}