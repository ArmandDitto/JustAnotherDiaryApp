package com.example.justordinarydiaryapp.utils.extension

import androidx.fragment.app.FragmentManager
import com.example.justordinarydiaryapp.base.presentation.BaseAlertDialog

fun FragmentManager.showErrorDialog(
    title: String? = null,
    desc: String? = null,
    btnPositiveText: String? = null,
    onPositiveBtnClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    withCloseIcon: Boolean = false,
    isCancelable: Boolean = false
) {
    val dialog = BaseAlertDialog()

    dialog.setContent(
        title = title ?: "Oops",
        desc = desc ?: "Something went wrong",
        imageSrc = null,
        btnPositiveText = btnPositiveText ?: "Retry",
        onPositiveBtnClick = onPositiveBtnClick,
        onDismiss = onDismiss,
        withCloseIcon = withCloseIcon,
        isCancelable = isCancelable
    )

    val ft = beginTransaction()
    ft.add(dialog, null)
    ft.commit()
}