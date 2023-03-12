package com.example.justordinarydiaryapp.data.repository

import com.example.justordinarydiaryapp.model.request.LoginRequest
import com.example.justordinarydiaryapp.model.response.LoginResponse
import com.example.justordinarydiaryapp.network.model.ResultWrapper

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest) : ResultWrapper<LoginResponse>

}