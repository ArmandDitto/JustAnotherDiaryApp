package com.example.justordinarydiaryapp.data.repository

import com.example.justordinarydiaryapp.model.User
import com.example.justordinarydiaryapp.model.request.LoginRequest
import com.example.justordinarydiaryapp.model.request.RegisterRequest
import com.example.justordinarydiaryapp.model.response.LoginResponse
import com.example.justordinarydiaryapp.network.ApiService
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.network.safeApiCall
import com.example.justordinarydiaryapp.utils.Constants
import com.example.justordinarydiaryapp.utils.preference.PreferencesHelper
import com.example.justordinarydiaryapp.utils.preference.PreferencesHelper.set
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): ResultWrapper<LoginResponse> {
        val result = safeApiCall(dispatcher) { apiService.login(loginRequest) }
        if (result is ResultWrapper.Success) {
            PreferencesHelper.authToken = result.value.accessToken
        }
        return result
    }

    override suspend fun register(registerRequest: RegisterRequest): ResultWrapper<User> {
        return safeApiCall(dispatcher) { apiService.register(registerRequest) }
    }


}