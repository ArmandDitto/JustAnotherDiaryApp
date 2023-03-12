package com.example.justordinarydiaryapp.model.request

import com.google.gson.annotations.SerializedName


data class RegisterRequest(

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("password_confirmation")
    var passwordConfirmation: String? = null

)
