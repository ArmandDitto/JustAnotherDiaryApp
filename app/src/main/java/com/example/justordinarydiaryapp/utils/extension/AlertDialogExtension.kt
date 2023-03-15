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
    isCancelable: Boolean = true
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

fun FragmentManager.showSuccessDialog(
    title: String? = null,
    desc: String? = null,
    btnPositiveText: String? = null,
    onPositiveBtnClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    withCloseIcon: Boolean = false,
    isCancelable: Boolean = true
) {
    val dialog = BaseAlertDialog()

    dialog.setContent(
        title = title ?: "Success",
        desc = desc ?: "Task Success",
        imageSrc = null,
        btnPositiveText = btnPositiveText ?: "Continue",
        onPositiveBtnClick = onPositiveBtnClick,
        onDismiss = onDismiss,
        withCloseIcon = withCloseIcon,
        isCancelable = isCancelable
    )

    val ft = beginTransaction()
    ft.add(dialog, null)
    ft.commit()
}

fun FragmentManager.showConfirmationDialog(
    title: String? = null,
    desc: String? = null,
    btnPositiveText: String? = null,
    onPositiveBtnClick: (() -> Unit)? = null,
    btnNegativeText: String? = null,
    onNegativeBtnClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    withCloseIcon: Boolean = false,
    isCancelable: Boolean = true
) {
    val dialog = BaseAlertDialog()

    dialog.setContent(
        title = title ?: "Confirmation",
        desc = desc ?: "Are you sure ?",
        imageSrc = null,
        btnPositiveText = btnPositiveText ?: "Yes",
        onPositiveBtnClick = onPositiveBtnClick,
        btnNegativeText = btnNegativeText ?: "No",
        onNegativeBtnClick = onNegativeBtnClick,
        onDismiss = onDismiss,
        withCloseIcon = withCloseIcon,
        isCancelable = isCancelable
    )

    val ft = beginTransaction()
    ft.add(dialog, null)
    ft.commit()
}