package com.example.justordinarydiaryapp.utils.extension

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.showDefaultToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()
}

fun View?.goneView() {
    this?.visibility = View.GONE
}

fun View?.visibleView() {
    this?.visibility = View.VISIBLE
}