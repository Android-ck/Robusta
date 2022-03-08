package com.zerir.robusta.presentation.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(toaster: Toast?, msg: String): Toast {
    toaster?.cancel()
    return Toast.makeText(this, msg, Toast.LENGTH_LONG)
}