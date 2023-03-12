package com.example.justordinarydiaryapp.model.response


import com.example.justordinarydiaryapp.model.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("user")
    val user: User?,

    @SerializedName("access_token")
    val accessToken: String?

)