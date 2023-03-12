package com.example.justordinarydiaryapp.network

import com.example.justordinarydiaryapp.model.User
import com.example.justordinarydiaryapp.model.request.LoginRequest
import com.example.justordinarydiaryapp.model.request.RegisterRequest
import com.example.justordinarydiaryapp.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ) : User

}