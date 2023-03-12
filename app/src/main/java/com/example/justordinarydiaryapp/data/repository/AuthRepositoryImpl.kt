package com.example.justordinarydiaryapp.data.repository

import com.example.justordinarydiaryapp.model.request.LoginRequest
import com.example.justordinarydiaryapp.model.response.LoginResponse
import com.example.justordinarydiaryapp.network.ApiService
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): ResultWrapper<LoginResponse> {
        return safeApiCall(dispatcher) { apiService.login(loginRequest) }
    }

}