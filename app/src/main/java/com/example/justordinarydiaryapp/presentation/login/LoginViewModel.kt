package com.example.justordinarydiaryapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justordinarydiaryapp.data.repository.AuthRepository
import com.example.justordinarydiaryapp.model.request.LoginRequest
import com.example.justordinarydiaryapp.model.response.LoginResponse
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _loginLiveData = MutableLiveData<ResultWrapper<LoginResponse>>()
    val loginLiveData: LiveData<ResultWrapper<LoginResponse>> = _loginLiveData

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginLiveData.postValue(ResultWrapper.Loading)
            _loginLiveData.postValue(repository.login(loginRequest))
        }
    }

}