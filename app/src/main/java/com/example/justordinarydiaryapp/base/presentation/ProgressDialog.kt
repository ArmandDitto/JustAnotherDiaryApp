package com.example.justordinarydiaryapp.base.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.window.OnBackInvokedDispatcher
import com.example.justordinarydiaryapp.R
import com.example.justordinarydiaryapp.databinding.LayoutProgressDialogBinding

object ProgressDialog {

    private var progressDialog: ProgressDialogImpl? = null

    fun show(context: Context, stringRes: Int = -1, onBackPress: OnProgressBackPressed? = null) {
        show(context, stringRes, onBackPress, true)
    }

    fun show(
        context: Context,
        stringRes: Int = -1,
        onBackPress: OnProgressBackPressed? = null,
        dismissPreviousDialog: Boolean
    ) {
        if (dismissPreviousDialog) {
            dismiss()
            progressDialog = ProgressDialogImpl(context).apply {
                setText(stringRes)
                onProgressBackPressed = onBackPress
                if (context is Activity && !context.isFinishing) show()
            }
        } else {
            var isToBeShown = false
            if (progressDialog == null) {
                progressDialog = ProgressDialogImpl(context)
                isToBeShown = true
            }
            progressDialog?.let { progressDialog ->
                if (stringRes != -1) progressDialog.setText(stringRes)
                progressDialog.onProgressBackPressed = onBackPress

                if (isToBeShown) {
                    if (context is Activity && !context.isFinishing) progressDialog.show()
                }
            }
        }
    }

    fun dismiss() {
        progressDialog?.let {
            if (it.isShowing) it.dismiss()
            progressDialog = null
        }
    }

    private class ProgressDialogImpl(val context: Context) {

        private val dialog: Dialog
        private val view: View
        private val viewBinding: LayoutProgressDialogBinding

        var onProgressBackPressed: OnProgressBackPressed? = null

        init {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                dialog = object : Dialog(context, R.style.MyCustomTheme_Transparent) {
                    @Suppress("OVERRIDE_DEPRECATION")
                    override fun onBackPressed() {
                        onProgressBackPressed?.invoke()
                    }
                }
            } else {
                dialog = Dialog(context, R.style.MyCustomTheme_Transparent)
                dialog.onBackInvokedDispatcher.registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_OVERLAY
                ) {
                    onProgressBackPressed?.invoke()
                }
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            @SuppressLint("InflateParams")
            view = LayoutInflater.from(context).inflate(R.layout.layout_progress_dialog, null)
            view.let { dialog.setContentView(it) }
            viewBinding = LayoutProgressDialogBinding.bind(view)
        }

        fun setText(stringRes: Int) {
            try {
                val text = context.resources.getString(stringRes)
                setText(text)
            } catch (e: Resources.NotFoundException) {
                setText(null)
            }
        }

        fun setText(message: String?) {
            viewBinding.tvLoading.text = message
            when {
                message.isNullOrEmpty() -> viewBinding.tvLoading.visibility = View.GONE
                else -> viewBinding.tvLoading.visibility = View.VISIBLE
            }
        }

        fun show() {
            dialog.show()
        }

        fun dismiss() {
            try {
                dialog.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val isShowing: Boolean
            get() = dialog.isShowing

    }

}

typealias OnProgressBackPressed = (() -> Unit)