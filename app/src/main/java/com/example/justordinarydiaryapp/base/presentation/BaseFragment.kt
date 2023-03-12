package com.example.justordinarydiaryapp.base.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.example.justordinarydiaryapp.R
import com.example.justordinarydiaryapp.utils.extension.showDefaultToast
import com.example.justordinarydiaryapp.utils.extension.showErrorDialog
import com.example.justordinarydiaryapp.utils.extension.showSuccessDialog

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private lateinit var activityLauncher: ActivityResultLauncher<Intent>
    private lateinit var intentSenderLauncher: ActivityResultLauncher<IntentSenderRequest>

    private var _binding: VB? = null
    protected val binding get() = requireNotNull(_binding)
    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    open fun shouldInterceptBackPress(): Boolean = false
    protected var onBackPressCallback: OnBackPressedCallback? = null

    open fun shouldAddBackStackListener(): Boolean = false
    protected var fragmentBackStackListener: FragmentManager.OnBackStackChangedListener? = null

    var hasInitializedRootView = false
    private var rootView: View? = null

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showToast(message: String) {
        requireContext().showDefaultToast(message)
    }

    fun showSuccessDialog(
        title: String? = null,
        desc: String? = null,
        btnPositiveText: String? = null,
        onPositiveBtnClick: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null,
        withCloseIcon: Boolean = false,
        isCancelable: Boolean = false
    ) {
        activity?.supportFragmentManager?.showSuccessDialog(
            title = title ?: getString(R.string.text_alert_dialog_title_success_default),
            desc = desc ?: getString(R.string.text_alert_dialog_desc_success_default),
            btnPositiveText = btnPositiveText ?: getString(R.string.text_action_continue),
            onPositiveBtnClick = onPositiveBtnClick,
            onDismiss = onDismiss,
            withCloseIcon = withCloseIcon,
            isCancelable = isCancelable
        )
    }

    fun showErrorDialog(
        title: String? = null,
        desc: String? = null,
        btnPositiveText: String? = null,
        onPositiveBtnClick: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null,
        withCloseIcon: Boolean = false,
        isCancelable: Boolean = false
    ) {
        activity?.supportFragmentManager?.showErrorDialog(
            title = title ?: getString(R.string.text_alert_dialog_title_error_default),
            desc = desc ?: getString(R.string.text_alert_dialog_desc_error_default),
            btnPositiveText = btnPositiveText ?: getString(R.string.text_action_retry),
            onPositiveBtnClick = onPositiveBtnClick,
            onDismiss = onDismiss,
            withCloseIcon = withCloseIcon,
            isCancelable = isCancelable
        )
    }

}