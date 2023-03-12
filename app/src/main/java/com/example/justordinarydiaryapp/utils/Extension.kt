package com.example.justordinarydiaryapp.utils

import android.content.Context
import android.widget.Toast

fun Context.showDefaultToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()
}