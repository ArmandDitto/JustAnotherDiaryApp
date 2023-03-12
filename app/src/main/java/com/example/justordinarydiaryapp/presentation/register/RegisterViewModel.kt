package com.example.justordinarydiaryapp.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justordinarydiaryapp.data.repository.AuthRepository
import com.example.justordinarydiaryapp.model.User
import com.example.justordinarydiaryapp.model.request.RegisterRequest
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository,
) : ViewModel() {

    var isEmailValid = false
    var isUsernameValid = false
    var isPasswordValid = false
    var isConfirmPasswordValid = false

    val isRegisterFormValid = MutableLiveData<Boolean?>()

    fun updateRegisterFormValidator() {
        val isValid = isEmailValid
                && isUsernameValid
                && isPasswordValid
                && isConfirmPasswordValid

        isRegisterFormValid.value = isValid
    }

    private val _registerLiveData = MutableLiveData<ResultWrapper<User>>()
    val registerLiveData: LiveData<ResultWrapper<User>> = _registerLiveData

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerLiveData.postValue(ResultWrapper.Loading)
            _registerLiveData.postValue(repository.register(registerRequest))
        }
    }

}