package com.example.justordinarydiaryapp.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.justordinarydiaryapp.R
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.base.presentation.ProgressDialog
import com.example.justordinarydiaryapp.databinding.FragmentRegisterBinding
import com.example.justordinarydiaryapp.model.request.RegisterRequest
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.presentation.login.LoginActivity
import com.example.justordinarydiaryapp.utils.FormValidator
import com.example.justordinarydiaryapp.utils.extension.setColoredText
import com.example.justordinarydiaryapp.utils.extension.setErrorText
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by viewModel()
    private val validator = FormValidator()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.isRegisterFormValid.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.btnRegister.isEnabled = it
            }
        }

        viewModel.registerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    ProgressDialog.show(requireContext())
                }

                is ResultWrapper.Success -> {
                    ProgressDialog.dismiss()
                    showSuccessDialog(
                        desc = "Register Success",
                        onDismiss = {
                            LoginActivity.launchIntent(requireContext())
                        }
                    )
                }

                is ResultWrapper.Error -> {
                    ProgressDialog.dismiss()
                    showErrorDialog(
                        desc = it.message,
                        onPositiveBtnClick = { sendData() }
                    )
                }
            }
        }
    }

    private fun initView() {

        binding.apply {
            btnRegister.setOnClickListener {
                sendData()
            }
        }

        binding.apply {
            txtEmail.doOnTextChanged { text, _, _, _ ->
                val error = validator.checkEmail(text.toString().trim())
                if (error.isNotEmpty()) {
                    viewModel.isEmailValid = false
                    txtEmailLayout.setErrorText(error)
                } else {
                    viewModel.isEmailValid = true
                    txtEmailLayout.setErrorText(null)
                }
                viewModel.updateRegisterFormValidator()
            }
        }

        binding.apply {
            txtUsername.doOnTextChanged { text, _, _, _ ->
                val error = validator.checkUsername(text.toString())
                if (error.isNotEmpty()) {
                    viewModel.isUsernameValid = false
                    txtUsernameLayout.setErrorText(error)
                } else {
                    viewModel.isUsernameValid = true
                    txtUsernameLayout.setErrorText(null)
                }
                viewModel.updateRegisterFormValidator()
            }
        }

        binding.apply {
            txtPassword.doOnTextChanged { text, _, _, _ ->
                val error = validator.checkPassword(text.toString())
                if (error.isNotEmpty()) {
                    viewModel.isPasswordValid = false
                    txtPasswordLayout.setErrorText(error)
                } else {
                    viewModel.isPasswordValid = true
                    txtPasswordLayout.setErrorText(null)
                    if (!txtConfirmPassword.text.isNullOrEmpty()) {
                        validateConfirmPassword(txtConfirmPassword.text.toString())
                    }
                }
                viewModel.updateRegisterFormValidator()
            }
        }

        binding.apply {
            txtConfirmPassword.doOnTextChanged { text, _, _, _ ->
                validateConfirmPassword(text.toString())
            }
        }

        binding.tvLoginAccount.setColoredText(
            message = getString(R.string.text_register_clickable_login_here),
            clickAction = {
                LoginActivity.launchIntent(requireContext())
            }
        )
    }

    private fun sendData() {
        binding.apply {
            viewModel.register(
                RegisterRequest(
                    txtEmail.text.toString(),
                    txtUsername.text.toString(),
                    txtPassword.text.toString(),
                    txtConfirmPassword.text.toString()
                )
            )
        }
    }

    private fun validateConfirmPassword(text: String?) {
        binding.apply {
            if (text.isNullOrEmpty()) {
                txtConfirmPasswordLayout.setErrorText(getString(R.string.text_error_form_field_is_required))
            } else if (txtPassword.text.isNullOrEmpty()) {
                txtConfirmPasswordLayout.setErrorText(getString(R.string.text_error_password_fill_password_first))
            } else if (text.toString() != txtPassword.text.toString()) {
                viewModel.isConfirmPasswordValid = false
                txtConfirmPasswordLayout.setErrorText(getString(R.string.text_error_password_unmatched_password))
            } else {
                viewModel.isConfirmPasswordValid = true
                txtConfirmPasswordLayout.setErrorText(null)
            }
            viewModel.updateRegisterFormValidator()
        }
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}