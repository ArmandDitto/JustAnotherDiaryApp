package com.example.justordinarydiaryapp.utils.extension

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setErrorText(message: String?) {
    error = message
    isErrorEnabled = !message.isNullOrEmpty()
}