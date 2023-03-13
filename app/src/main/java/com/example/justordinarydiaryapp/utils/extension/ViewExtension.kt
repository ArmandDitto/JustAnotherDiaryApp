package com.example.justordinarydiaryapp.utils.extension

import android.app.Activity
import android.os.Build
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.widget.Toolbar
import com.example.justordinarydiaryapp.R
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setErrorText(message: String?) {
    error = message
    isErrorEnabled = !message.isNullOrEmpty()
}

fun Toolbar.setNavigationAsBack(activity: Activity, backListener: (() -> Unit)? = null) {
    setNavigationIcon(R.drawable.ic_arrow_back)
    setNavigationOnClickListener {
        if (backListener != null) {
            backListener.invoke()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                activity.onBackInvokedDispatcher.registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_OVERLAY
                ) { activity.finish() }
            } else {
                @Suppress("DEPRECATION")
                activity.onBackPressed()
            }
        }
    }
}