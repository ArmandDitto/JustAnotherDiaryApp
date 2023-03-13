package com.example.justordinarydiaryapp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.justordinarydiaryapp.R
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.base.presentation.ProgressDialog
import com.example.justordinarydiaryapp.databinding.FragmentLoginBinding
import com.example.justordinarydiaryapp.model.request.LoginRequest
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.presentation.home.HomeActivity
import com.example.justordinarydiaryapp.presentation.register.RegisterActivity
import com.example.justordinarydiaryapp.utils.ValidationHelper
import com.example.justordinarydiaryapp.utils.extension.setColoredText
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.loginLiveData.observe(this) {
            when (it) {
                is ResultWrapper.Loading -> {
                    ProgressDialog.show(requireContext())
                }

                is ResultWrapper.Success -> {
                    ProgressDialog.dismiss()
                    HomeActivity.launchIntent(requireContext())
                }

                is ResultWrapper.Error -> {
                    ProgressDialog.dismiss()
                    showErrorDialog(
                        desc = it.message,
                        btnPositiveText = getString(R.string.text_action_close),
                        isCancelable = true
                    )
                }
            }
        }
    }

    private fun initView() {

        binding.apply {
            txtEmail.doOnTextChanged { text, _, _, _ ->
                val input = text.toString()
                if (input.isNotEmpty()) {
                    binding.txtEmailLayout.error = null
                }
            }
        }

        binding.apply {
            txtPassword.doOnTextChanged { text, _, _, _ ->
                val input = text.toString()
                if (input.isNotEmpty()) {
                    binding.txtPasswordLayout.error = null
                }
            }
        }

        binding.tvRegisterAccount.setColoredText(
            message = getString(R.string.text_login_clickable_register_here),
            clickAction = {
                RegisterActivity.launchIntent(requireContext())
            }
        )

        binding.btnLogin.setOnClickListener {
            if (isDataValid) {
                viewModel.login(
                    LoginRequest(
                        binding.txtEmail.text.toString(),
                        binding.txtPassword.text.toString()
                    )
                )
            } else {
                showToast(requireContext().getString(R.string.text_error_form_remaining_field))
            }
        }
    }

    private val isDataValid: Boolean
        get() {
            binding.apply {
                var isValid = true
                txtPassword.clearFocus()
                txtPasswordLayout.error = null

                if (ValidationHelper.isBlank(txtEmail)) {
                    txtEmail.requestFocus()
                    txtEmailLayout.error = getString(R.string.text_error_form_field_is_required)
                    isValid = false
                } else if (!ValidationHelper.isValidEmail(txtEmail)) {
                    txtEmail.requestFocus()
                    txtEmailLayout.error = getString(R.string.text_error_email_invalid_email)
                    isValid = false
                }

                if (ValidationHelper.isBlank(txtPassword)) {
                    txtPassword.requestFocus()
                    txtPasswordLayout.error = getString(R.string.text_error_form_field_is_required)
                    isValid = false
                } else if (!ValidationHelper.isValidPassword(txtPassword)) {
                    txtPassword.requestFocus()
                    txtPasswordLayout.error =
                        getString(R.string.text_error_password_alphanumeric_only)
                    isValid = false
                }

                return isValid
            }
        }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}